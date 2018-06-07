export class GameState {
  game_Id: number;
  deckState: any;
  currentTurn: number;
  pot: number;
  status: number;
  timeLeft: number;
  tableState: any;

  constructor(game_Id: number, deckState: any, currentTurn: number, pot: number, status: number, timeLeft: number, tableState: any) {
    this.game_Id = game_Id;
    this.deckState = deckState;
    this.currentTurn = currentTurn;
    this.pot = pot;
    this.status = status;
    this.timeLeft = timeLeft;
    this.tableState = tableState;
  }
}
