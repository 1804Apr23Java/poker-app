import { Component, OnInit } from '@angular/core';
import { NavBarService } from '../../services/nav-bar.service';
import { Statistics } from '../../models/statistics.model';
import { UserDecision } from '../../models/user-decision.model';
import { UserStatsService } from '../../services/user-stats.service';
import { UserActionService } from '../../services/user-action.service';
import { Player } from '../../models/Player';

@Component({
  selector: 'app-canvas',
  templateUrl: './gameboard.component.html',
  styleUrls: ['./gameboard.component.css']
})

export class GameboardComponent implements OnInit {

  public userInfo: Statistics;
  public currentTurnOrder: number;

  private canvas: HTMLCanvasElement;
  private ctx: CanvasRenderingContext2D;

  private width: number;
  private height: number;
  private halfWidth: number;
  private halfHeight: number;

  private cardWidth: number;
  private cardHeight: number;

  private cardSpace: number;
  private playerSapce: number;
  private margin: number;

  private user: Player;
  private board: string[];
  private otherPlayers: Player[];

  private pot: number;

  constructor(public nav: NavBarService, private userStatsService: UserStatsService, private userActionService: UserActionService) {
  }

  // userid: this.userInfo.user.userId

  ngOnInit() {
    this.pot = 0;
    this.getUserInformation();
    this.nav.show();

    this.setCanvas(<HTMLCanvasElement> document.getElementById('thisCanvas'));
    this.drawBoardBackground();

    let user = new Player("Jack77", "Check", "$1000", ["JH", "7H"], 0);
    this.setUser(user);

    this.drawUserHand();
    this.drawUserInfo();

    let board = ['KH', 'QS', '6D', 'JC', 'TD'];
    this.setBoard(board);
    this.drawBoard();

    let p = [ new Player("Jack777777777777777", "Check", "$1000", ["KD", "6S"], 1),
              new Player("Jane01", "Raise 250", "$1500", ["BC", "BC"], 2),
              new Player("Rob25", "fold", "$275", ["BC", "BC"], 3),
              new Player("Sue182", "fold", "$990", ["BC", "BC"], 4),
              new Player("Jack77", "Check", "$1000", ["GC", "GC"], 5),
              new Player("Jack77", "Check", "$1000", ["BC", "BC"],6)];
    this.setOtherPlayers(p);
    this.drawOtherPlayers();

    this.refreshBoard();
  }

  // public userAction(action) {
  //   if (this.user.turnOrder !== this.currentTurnOrder) {
  //     return;
  //   }
  //
  //   const url = 'https://pokerapp.cfapps.io/currentHands/action';
  //   const xhr = new XMLHttpRequest();
  //
  //   xhr.open('POST', url, true);
  //   xhr.onreadystatechange = function() {
  //     if (this.readyState === 4 && this.status === 200) { }
  //     console.log(action)
  //
  //     xhr.send(action);
  //   };
  // }

  public userAction(decision: string) {
    const userDecision: UserDecision = new UserDecision(decision);
    this.userActionService.postUserDecision(userDecision).subscribe(message => console.log(message));
  }

  drawPot() {
    let x = (this.width / 6);
    let y = this.halfHeight;

    this.ctx.fillStyle = 'gold';
    this.ctx.font = (this.cardWidth / 2) + 'px fancy';

    this.ctx.textAlign = 'center';
    this.ctx.fillText('$' + this.pot, x, y);
  }

  refreshBoard() {
    setInterval(() => {
      const url: string = "https://pokerapp.cfapps.io/currentHands/getFullGameState/" + this.userInfo.user.userId;
      this.sendAjaxGet(this, url)
    }, 10000);

  }

  sendAjaxGet(obj: GameboardComponent, url: string): void {
      const xhr = new XMLHttpRequest();

      xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
          let response = JSON.parse(xhr.responseText);
          console.log(response);
          obj.currentTurnOrder = response.user.user.gameStates.currentTurn;
          obj.pot = response.otherPlayers[0].user.gameStates.pot;
          let newUser = new Player(response.user.user.username, response.user.status,
                                   response.user.winnings, response.user.hand.split(' '), response.user.playerOrder);
          obj.user = newUser;

          let players = response.otherPlayers;
          let p = [];
          for (let i = 0; i < players.length; i++) {
            p.push(new Player(players[i].user.username, players[i].status, players[i].winnings, players[i].hand.split(' '), players[i].playerOrder));
          }
          obj.otherPlayers = p;

          let boardCards = response.tableState.split(' ');
          obj.board = boardCards;

          obj.drawBoardBackground();
          obj.drawUserHand();
          obj.drawUserInfo();
          obj.drawBoard();
          obj.drawOtherPlayers();
        }
      };
      xhr.open('GET', url, true);
      xhr.send();
    }

    getUserInformation(): void {
      this.userStatsService.fetchStatsInformation()
        .subscribe(
          (userInfo: Statistics) => {
            this.userInfo = userInfo;
            // console.log(this.userInfo);
          },
          error => { console.log(error); }
        );
      }

  /* Sets the Canvas for the Gameboard; also sets the sizing for gameboard elements.
   */
  setCanvas(canvas: HTMLCanvasElement) {
    this.canvas = canvas;
    this.ctx = canvas.getContext('2d');
    this.width = canvas.width;
    this.height = canvas.height;
    this.cardHeight = (this.height / 4);
    this.cardWidth = (this.cardHeight * 63.5 / 88.9);
    this.cardSpace = (this.width / 300);
    this.playerSapce = (this.cardWidth / 2);
    this.halfWidth = (this.width / 2);
    this.halfHeight = (this.height / 2);
    this.margin = (this.width / 35);
  }

   /* Draws a blank green rectangle over the canvas.
    */
  drawBoardBackground() {
    let image = new Image(this.width, this.height);
    image.src = '../../assets/images/game/table.svg';
    image.onload = this.getBackgroundClosure(image, 0, 0, this.width, this.height);
  }

   /* Sets the user object for the viewing player.
    */
  setUser(user: Player) {
    this.user = user;
  }

   /* Draws the hand for the viewing player.
    */
  drawUserHand() {
    let cardOneTopLeftX = (this.halfWidth - this.cardWidth) - (this.cardSpace / 2);
    let cardOneTopLeftY = (this.height - this.cardHeight) - this.margin;
    let cardTwoTopLeftX = this.halfWidth + (this.cardSpace / 2);
    let cardTwoTopLeftY = cardOneTopLeftY;

    this.drawCard(this.cardWidth, this.cardHeight, this.user.hand[0], cardOneTopLeftX, cardOneTopLeftY);
    this.drawCard(this.cardWidth, this.cardHeight, this.user.hand[1], cardTwoTopLeftX, cardTwoTopLeftY);
  }

  /* Draws the information for the player viewing the gameboard.
   */
  drawUserInfo() {
    this.drawInfo(this.halfWidth, this.height - (this.margin / 1.1), this.user);
  }

   /* Sets the board cards.
    */
  setBoard(board: string[]) {
    this.board = board;
  }

   /* Draws the board cards on the center of the gameboard.
    */
  drawBoard() {
    let numCards = this.board.length;

    //Calculate the starting coordinates for the cards
    let totalWidth = ((this.cardWidth * numCards) + (this.cardSpace * (numCards - 1)));
    let x = this.halfWidth - (totalWidth / 2);
    let y = this.halfHeight - (this.cardHeight /2);

    for(let i = 0; i < numCards; i++) {
      this.drawCard(this.cardWidth, this.cardHeight, this.board[i], x, y);
      x += (this.cardWidth + this.cardSpace);
    }
  }

  /* Sets the other players in the game.
   */
  setOtherPlayers(otherPlayers: Player[]) {
    this.otherPlayers = otherPlayers;
  }

  /* Draws the other players cards on the gameboard.
   */
  drawOtherPlayers() {
    let numPlayers = this.otherPlayers.length;

    if(numPlayers > 4) {
      let middleCount = numPlayers - 2;
      let totalWidth = ((this.cardWidth * 2) * middleCount) + (this.cardSpace * middleCount) + (this.playerSapce * (middleCount - 1));

      //First player
      let x = this.halfWidth - (totalWidth / 2);
      let y = (this.height - this.cardHeight - this.margin);

      this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[0].hand[0], x, y);
      x+= this.cardWidth + this.cardSpace;
      this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[0].hand[1], x, y);

      totalWidth = ((this.cardWidth * 2) * middleCount) + (this.cardSpace * middleCount) + (this.playerSapce * (middleCount - 1));
      x = this.halfWidth - (totalWidth / 2);
      y = this.margin;

      for(let i = 1; i < (numPlayers - 1); i++) {
        this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[i].hand[0], x, y);
        x += this.cardWidth + this.cardSpace;
        this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[i].hand[0], x, y);
        x += this.cardWidth + this.playerSapce;
      }

      //Last player
      x -= (this.cardWidth + this.cardSpace + this.playerSapce);
      y = (this.height - this.cardHeight - this.margin);

      this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[numPlayers - 1].hand[1], x, y);
      x-= this.cardWidth + this.cardSpace;
      this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[numPlayers - 1].hand[0], x, y);
    } else {
      let totalWidth = ((this.cardWidth * 2) * numPlayers) + (this.cardSpace * numPlayers) + (this.playerSapce * (numPlayers - 1));

      let startX = (this.width / 2) - (totalWidth / 2);
      let startY = this.margin;

      for(let i = 0; i < numPlayers; i++) {
        this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[i].hand[0], startX, startY);
        startX += this.cardWidth + this.cardSpace;
        this.drawCard(this.cardWidth, this.cardHeight, this.otherPlayers[i].hand[1], startX, startY);
        startX += this.cardWidth + this.playerSapce;
      }
    }
  }

  /* Draws information for the other players on the board.
   */
  drawPlayerInfo() {
    let numPlayers = this.otherPlayers.length;

    if(numPlayers > 4) {
      let middleCount = numPlayers - 2;
      let totalWidth = ((this.cardWidth * 2) * middleCount) + (this.cardSpace * middleCount) + (this.playerSapce * (middleCount - 1));

      //First player
      let x = (this.halfWidth - (totalWidth / 2)) + (((this.cardWidth * 2) + this.cardSpace) / 2);
      let y = this.height - (this.margin / 1.1);
      this.drawInfo(x, y, this.otherPlayers[0]);

      let jump = (((this.cardWidth * 2) + this.cardSpace)/ 2);
      x = (this.halfWidth - (totalWidth / 2)) + jump;
      y = 2;

      for(let i = 0; i < numPlayers; i++) {
        this.drawInfo(x, y, this.otherPlayers[i]);
        x += ((jump * 2) + this.playerSapce);
      }

      //Last player
      x = (this.halfWidth + (totalWidth / 2)) - jump;
      y = this.height - (this.margin / 1.1);
      this.drawInfo(x, y, this.otherPlayers[numPlayers - 1]);
    } else {
      let jump = (((this.cardWidth * 2) + this.cardSpace)/ 2);
      let totalWidth = ((this.cardWidth * 2) * numPlayers) + (this.cardSpace * numPlayers) + (this.playerSapce * (numPlayers - 1));
      let startX = (this.halfWidth - (totalWidth / 2)) + jump;
      let startY = 2;

      for(let i = 0; i < numPlayers; i++) {
        this.drawInfo(startX, startY, this.otherPlayers[i]);
        startX += ((jump * 2) + this.playerSapce);
      }
    }
  }

  /* Draws user info to the specified location on the board.
   *
   * x: The x-coordinate where info text drawing begins.
   * y: The y-coordinate where info text drawing begins.
   * p: The Player object containing the information to display.
   */
  private drawInfo(x: number, y: number, p: Player) {
    let maxUserWidth = (this.cardWidth / 2);
    let size = (this.margin / 1.25);
    x -= ((maxUserWidth / 2) + size);

    let image = new Image(size, size);
    image.src = '../../assets/images/game/orn.svg';
    image.onload = this.getClosure(image, x, y, size, size);

    this.ctx.fillStyle = 'gold';
    this.ctx.font = (this.cardWidth / 10) + 'px fancy';
    x += size + (maxUserWidth / 2);
    y += (this.margin / 2);
    this.ctx.textAlign = 'center';
    this.ctx.fillText(p.username, x, y, maxUserWidth);

    x+= (maxUserWidth / 2);
    y -= (this.margin / 2);

    let image2 = new Image(size, size);
    image2.src = '../../assets/images/game/orn2.svg';
    image2.onload = this.getClosure(image2, x, y, size, size);
  }

  /* Draws an SVG card image on the canvas.
   *
   * cardWidth: The width of the card in pixels.
   * cardHeight: The height of the card in pixels.
   * cardName: The filename of the card, exluding the .SVG extension.
   * x: The upper-left x-coordinate of the image.
   * y: The upper-left y-coordinate of the image.
   */
  private drawCard(cardWidth: number, cardHeight: number, cardName: string, x: number, y:number) {
    let image = new Image(cardWidth, cardHeight);
    image.src = '../../assets/images/game/' + cardName + '.svg';
    image.onload = this.getClosure(image, x, y, cardWidth, cardHeight);
  }

  /* Returns a closure containing two coordinates and an image; the closure can be used by
   * HTMLImageElement.onload to draw an image to the correct part of the canvas.
   *
   * image: The image to draw to the canvas.
   * x: The upper-left x-coordinate of the image.
   * y: The upper-left y-coordinate of the image.
   */
  private getClosure(image: HTMLImageElement, x: number, y:number, dx:number, dy:number): any {
    return () => {
      this.ctx.drawImage(image, x, y, dx, dy);
      this.drawPot();

    };
  }

  /* Returns a closure containing two coordinates and an image; the closure can be used by
   * HTMLImageElement.onload to draw an image to the correct part of the canvas.
   *
   * image: The image to draw to the canvas.
   * x: The upper-left x-coordinate of the image.
   * y: The upper-left y-coordinate of the image.
   */
  private getBackgroundClosure(image: HTMLImageElement, x: number, y:number, dx:number, dy:number): any {
    return () => {
      this.ctx.drawImage(image, x, y, dx, dy);
      this.drawPlayerInfo();
      this.drawUserInfo();
    };
  }
}
