export class Player {
    username: string;
    status: string;
    winnings: string;
    hand: [string, string];
    turnOrder: number;

    constructor(username: string, status:string, winnings: string, hand: [string, string], turnOrder: number) {
        this.username = username;
        this.status = status;
        this.winnings = winnings;
        this.hand = hand;
        this.turnOrder = turnOrder;
    }
}
