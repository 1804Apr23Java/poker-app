import { UserInfo } from './user-info.model';

export class CurrentHand {

  user: UserInfo;
  hand: any;
  winnings: number;
  hasFolded: boolean;
  playerOrder: number;

  constructor(user: UserInfo, hand: any, winnings: number, hasFolded: boolean, playerOrder: number) {
    this.user = user;
    this.hand = hand;
    this.winnings = winnings;
    this.hasFolded = hasFolded;
    this.playerOrder = playerOrder;
  }
}
