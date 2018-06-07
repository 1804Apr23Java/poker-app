import { GameState } from './game-state.model';

export class UserInfo {
  userId: number;
  gameStates: GameState;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  username: string;
  isHost: boolean;

constructor(userId: number, gameStates: GameState, username: string, firstName: string, lastName: string,
email: string, password: string, isHost: boolean) {
      this.userId = userId;
      this.gameStates = gameStates;
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.isHost = isHost;

  }
}
