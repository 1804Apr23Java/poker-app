package beans;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.beans.CurrentHands;
import com.revature.beans.GameStates;
import com.revature.beans.Users;
import com.revature.service.CurrentHandsService;
import com.revature.service.GameStatesService;
import com.revature.service.UsersService;

import beans.Player.playerAction;

public class Game {
	private ArrayList<Users> players;
	private ArrayList<CurrentHands> playerHands;
	
	private ArrayList<String> communityCards;
	private int gameId;
	
	private UsersService usersService;
	private CurrentHandsService currentHandsService;
	private GameStatesService gameStatesService;
	
	private HashMap<Character, Integer> cardValue;
	
	public Game(int gameId) {
		this.gameId = gameId;
		this.usersService = new UsersService();
		this.currentHandsService = new CurrentHandsService();
		this.gameStatesService = new GameStatesService();
		cardValue = new HashMap<Character, Integer>();
		cardValue.put('2', 2);
		cardValue.put('3', 3);
		cardValue.put('4', 4);
		cardValue.put('5', 5);
		cardValue.put('6', 6);
		cardValue.put('7', 7);
		cardValue.put('8', 8);
		cardValue.put('9', 9);
		cardValue.put('T', 10);
		cardValue.put('J', 11);
		cardValue.put('Q', 12);
		cardValue.put('K', 13);
		cardValue.put('A', 14);
	}
	
	/**
	 * Starts game.
	 */
	public void game() {
		getAllPlayers();
		initPlayers();
		initGameState();
		
		while(doesGameContinue()) {
			setupPlayers(); // Assign player order and reset last action to not be fold.
			newRound(); // Start a new round.
		}
	}
	
	private void getAllPlayers() {
		players = usersService.getUsersWithGameId(gameId);
		
		ArrayList<CurrentHands> hands = new ArrayList<>();
		for(Users u : players) {
			hands.add(currentHandsService.getCurrentHandById(u.getuserId())); // IS THIS HOW getCurrentHandById works ????????
		}
	}
	
	private void initPlayers() {
		int i = 0;
		for(CurrentHands c : playerHands) {
			c.setHand("BC BC");
			c.setHasFolded(false);
			c.setPlayerOrder(i++);
			c.setWinnings(0);
			
			currentHandsService.updateCurrentHand(c);
		}
	}
	
	private void initGameState() {
		GameStates game = gameStatesService.getGameStatesById(gameId);
		game.setCurrentTurn(0);
		game.setDeckState(Deck.shuffleDeck());
		game.setPot(0);
		game.setTableState("");
	}
	
	/**
	 * Determines if game is over.
	 * @return true if game continues, false if game over.
	 */
	private boolean doesGameContinue() {
		int remainingPlayers = 0;
		getAllPlayers();
		
		for(CurrentHands c : playerHands) {
			remainingPlayers = (c.getWinnings() > 0) ? (remainingPlayers + 1) : remainingPlayers;
		}
		return (remainingPlayers > 1) ? true : false;
	}
	
	public void newRound() {
		//controls game flow
		dealToPlayers(); // Deal to cards to each player.
		
		boolean shouldContinue = cycle(); // Cycle through every player.
		if(!shouldContinue) { // Determine if round is over.
			earlyRoundEnd(); // Handle early round end.
			return;
		}
		
		dealFlop(); // Deal the flop
		
		shouldContinue = cycle(); // Cycle through every player.
		if(!shouldContinue) { // Determine if round is over.
			earlyRoundEnd(); // Handle early round end.
			return;
		}
		
		dealTurn(); // Deal the turn
		
		shouldContinue = cycle(); // Cycle through every player.
		if(!shouldContinue) { // Determine if round is over.
			earlyRoundEnd(); // Handle early round end.
			return;
		}
		
		dealRiver(); // Deal the river
		
		shouldContinue = cycle(); // Cycle through every player.
		if(!shouldContinue) { // Determine if round is over.
			earlyRoundEnd(); // Handle early round end.
			return;
		}
			
		ArrayList<CurrentHands> winners = declareRoundWinner(); // Determine round winners.
		persistWinnings(winners); // Persist the winners to the database.
	}
	
	private void earlyRoundEnd() {
		CurrentHands winner = determineWinner();
		ArrayList<CurrentHands> soleWinner = new ArrayList<>();
		soleWinner.add(winner);
		persistWinnings(soleWinner);
	}
	
	private CurrentHands determineWinner() {
		for (CurrentHands c : playerHands) {
			c = currentHandsService.getCurrentHandById(c.getUser().getuserId());
			if (!c.isHasFolded()) {
				return c;
			}
		}
		return null;	
	}
	
	private void persistWinnings(ArrayList<CurrentHands> winners) {
		GameStates game = gameStatesService.getGameStatesById(gameId);
		int winnings = game.getPot();
		int winningsDivided = (int) (winnings / winners.size());
		
		for (CurrentHands c : winners) {
			c.setWinnings(winningsDivided);
			currentHandsService.updateCurrentHand(c);
		}
	}
	
	public void dealToPlayers() {
		GameStates game = gameStatesService.getGameStatesById(gameId);
		String deckState = game.getDeckState();
		
		for (CurrentHands c: playerHands) {
			c.setHand(deckState.substring(0, 5));
			deckState = deckState.substring(6);
			currentHandsService.updateCurrentHand(c);
		}
		
		game.setDeckState(deckState);
		gameStatesService.updateGameState(game);
	}
	
	public void dealFlop() {
		GameStates game = gameStatesService.getGameStatesById(gameId);
		String deckState = game.getDeckState();
		
		game.setTableState(deckState.substring(0, 8));
		game.setDeckState(deckState.substring(9));
	}
	
	public void dealTurn() {
		GameStates game = gameStatesService.getGameStatesById(gameId);
		String deckState = game.getDeckState();
		String tableState = game.getTableState();
		
		game.setTableState(tableState + " " + deckState.substring(0, 2));
		game.setDeckState(deckState.substring(3));
	}
	
	public void dealRiver() {
		GameStates game = gameStatesService.getGameStatesById(gameId);
		String deckState = game.getDeckState();
		String tableState = game.getTableState();
		
		game.setTableState(tableState + " " + deckState.substring(0, 2));
		game.setDeckState(deckState.substring(3));
	}
	
	public boolean cycle() {
		for (CurrentHands c : playerHands) {
			
			c = currentHandsService.getCurrentHandById(c.getUser().getuserId());
			if (!c.isHasFolded()) {
				GameStates g = gameStatesService.getGameStatesById(gameId);
				g.setCurrentTurn(c.getPlayerOrder());
				askPlayer(c);
			}
			
			if(!doesCycleContinue()) return false;
		}
		return true;
	}
	
	public void askPlayer(CurrentHands c) {
		int currentTurn = 0;
		do {
			GameStates g = gameStatesService.getGameStatesById(gameId);
			currentTurn = g.getCurrentTurn();
			
			if(currentTurn > players.size()-1) {
				currentTurn = 0;
				g.setCurrentTurn(0);
				gameStatesService.updateGameState(g);
			} else if(playerHands.get(currentTurn).isHasFolded()) {
				
				while(playerHands.get(currentTurn).isHasFolded()) {
					currentTurn++;
					if(currentTurn > players.size()-1) {
						currentTurn = 0;
						g.setCurrentTurn(currentTurn);
						gameStatesService.updateGameState(g);
						return;
					}
				}			
			}
			
			g.setCurrentTurn(currentTurn);
			gameStatesService.updateGameState(g);
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while(currentTurn == c.getPlayerOrder());
	}
	
	private void setupPlayers() {
		int i = 0;
		for(CurrentHands c : playerHands) {
			c.setHand("BC BC");
			c.setHasFolded(false);
			c.setPlayerOrder(i++);
			currentHandsService.updateCurrentHand(c);
		}
	}
	
	public boolean doesCycleContinue() {
		int count = 0;
		for (CurrentHands c : playerHands) {
			c = currentHandsService.getCurrentHandById(c.getUser().getuserId());
			if (!c.isHasFolded()) {
				count++;
			};
		}
		return (count > 1);
	}
	
	public ArrayList<CurrentHands> declareRoundWinner() {
		int maxScore = 0;
		ArrayList<CurrentHands> winner = new ArrayList<>();
		for (CurrentHands c : playerHands) {
			if (!c.isHasFolded()) {
				int score = determinePlayerBestHand(c.getHand(), gameStatesService.getGameStatesById(gameId).getTableState());
				if (score > maxScore) {
					winner.clear();
					winner.add(c);
					maxScore = score;					
				} else if (score == maxScore) {
					winner.add(c);
				}
			}
		}
		return winner;
	}
	
	public int determinePlayerBestHand(String hand, String communityCards) {
		String[] h = hand.split(" ");
		ArrayList<String> userHand = new ArrayList<>();
		userHand.add(h[0]);
		userHand.add(h[1]);
		
		ArrayList<String> possibleHand = new ArrayList<>();
		possibleHand.addAll(userHand);
		
		h = communityCards.split(" ");
		possibleHand.add(h[0]);
		possibleHand.add(h[1]);
		possibleHand.add(h[2]);
		possibleHand.add(h[3]);
		possibleHand.add(h[4]);
		
		sortHand(possibleHand);
		
		int handValue = 0;
		
		boolean flush = checkFlush(possibleHand);
		if(flush) {
			handValue += 10_000_000;
		}
		
		int val = checkStraight(possibleHand);
		if(val > 0) {
			handValue += 8_000_000;
			handValue += (val * 10_000);
			return handValue;
		} else if(flush) {
			return handValue;
		}
		
		val = checkFourOfAKind(possibleHand);
		if(val > 0) {
			handValue += 14_000_000;
			handValue += (val * 10_000);
			return handValue;
		}
		
		val = checkThreeOfAKind(possibleHand);
		// Check for full house, or three of a kind
		if(val > 0) {
			int val2 = checkPair(possibleHand, val);
			if(val2 > 0) {
				handValue += 12_000_000;
				handValue += (val * 10_000);
				handValue += (val2 * 100);
				return handValue;
			} else {
				handValue += 6_000_000;
				handValue += (val * 10_000);
				return handValue;
			}
		}
		
		val = checkPair(possibleHand, 0);
		if(val > 0) {
			int val2 = checkPair(possibleHand, val);
			if(val2 > 0) {
				handValue += 4_000_000;
				handValue += (val * 10_000);
				handValue += (val2 * 100);
				int hc = getHighCard(userHand);
				handValue += hc;
				return handValue;
			} else {
				handValue += 2_000_000;
				handValue += (val * 10_000);
				int hc = getHighCard(userHand);
				handValue += hc;
				return handValue;
			}
		}
		return getHighCard(userHand);
	}
	
	public int getHighCard(ArrayList<String> hand) {
		int highCard = 0;
		for(String s: hand) {
			highCard += cardValue.get(s.charAt(0));
		}
		return highCard;
	}
	
	public int checkPair(ArrayList<String> hand, int ignore) {
		ArrayList<Integer> cardValues = new ArrayList<>();
		for (String s : hand) {
			cardValues.add(cardValue.get(s.charAt(0)));
		}
	
		//check for pair
		int maxCard = 0;
		int count = 1;
		for(int x = 1; x <= 6; x++) {
			if(cardValues.get(x) == ignore) {
				continue;
			}
			if(cardValues.get(x) != cardValues.get(x-1)) {
				count = 1;
			} else {
				count++;
				if (count == 2) {
					maxCard = cardValues.get(x);
				}
			}
		}
		return maxCard;
	}
	
	public int checkThreeOfAKind(ArrayList<String> hand) {
		ArrayList<Integer> cardValues = new ArrayList<>();
		for (String s : hand) {
			cardValues.add(cardValue.get(s.charAt(0)));
		}
	
		//check for three of a kind
		int maxCard = 0;
		int count = 1;
		for(int x = 1; x <= 6; x++) {
			if(cardValues.get(x) != cardValues.get(x-1)) {
				count = 1;
			} else {
				count++;
				if (count == 3) {
					maxCard = cardValues.get(x);
				}
			}
		}
		return maxCard;
	}
		
	public boolean checkFlush(ArrayList<String> hand) {
		int spades = 0;
		int hearts = 0;
		int diamonds = 0;
		int clubs = 0;
		for(String s : hand) {
			if (s.charAt(1) == 'S') {
				spades++;
			} else if (s.charAt(1) == 'H') {
				hearts++;
			} else if (s.charAt(1) == 'D') {
				diamonds++;
			} else if (s.charAt(1) == 'C') {
				clubs++;
			}
		}
		if (spades > 4 || hearts > 4 || diamonds > 4 || clubs > 4) {
			return true;
		} else return false;
	}
	
	public int checkFourOfAKind(ArrayList<String> hand) {
		ArrayList<Integer> cardValues = new ArrayList<>();
		for (String s : hand) {
			cardValues.add(cardValue.get(s.charAt(0)));
		}
	
		//check for four of a kind
		int maxCard = 0;
		int count = 1;
		for(int x = 1; x <= 6; x++) {
			if(cardValues.get(x) != cardValues.get(x-1)) {
				count = 1;
			} else {
				count++;
				if (count == 4) {
					maxCard = cardValues.get(x);
					return maxCard;
				}
			}
		}
		return maxCard;
	}
	
	public int checkStraight(ArrayList<String> hand) {
		ArrayList<Integer> cardValues = new ArrayList<>();
		//convert to card values
		for (String s : hand) {
			cardValues.add(cardValue.get(s.charAt(0)));
		}
		//Quick check if not straight
		if ((cardValues.get(4) - 1) != cardValues.get(3)) {
			return 0;		
		}
		//check for straight
		int maxCard = 0;
		int count = 1;
		for(int x = 1; x <= 6; x++) {
			if((cardValues.get(x) - 1) != cardValues.get(x - 1)) {
				count = 1;
			} else {
				count++;
				if (count >= 5) {
					maxCard = cardValues.get(x);
				}
			}
		}
		return maxCard;
	}
		
	public void sortHand(ArrayList<String> hand) {
		hand.sort((card1, card2) -> {
			int c1Val = cardValue.get(card1.charAt(0));
			int c2Val = cardValue.get(card2.charAt(0));
			if(c1Val == c2Val) return 0;
			return (c1Val < c2Val) ? -1 : 1;
		});
	}

	public ArrayList<String> getCommunityCards() {
		return communityCards;
	}

	public void setCommunityCards(ArrayList<String> communityCards) {
		this.communityCards = communityCards;
	}
}