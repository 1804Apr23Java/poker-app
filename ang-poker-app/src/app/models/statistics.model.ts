import { UserInfo } from './user-info.model';
import { GameState } from '../models/game-state.model';

export class Statistics {

  user: UserInfo;
  totalWinnings: number;
  wins: number;
  losses: number;

  constructor(user: UserInfo, totalWinnings: number, wins: number, losses: number) {
    this.user = user;
    this.totalWinnings = totalWinnings;
    this.wins = wins;
    this.losses = losses;
  }
}
