package com.revature.requestHelper;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.beans.CurrentHands;
import com.revature.service.CurrentHandsService;
import com.revature.service.GameStatesService;

public class UserAction {
	
	@Autowired
	private static CurrentHandsService currentHandsService;
	
	@Autowired
	private static GameStatesService gameStatesService;

	public static String checkUserAction(String action, CurrentHands currentHand) {
		// Increment Player Order
		currentHand.getUser().getGameStates().setCurrentTurn(currentHand.getPlayerOrder() + 1);

		if (action.equals("check")) {
			// Do nothing
			// Save changes to DB
			gameStatesService.updateGameState(currentHand.getUser().getGameStates());
			System.out.println("User has checked!");
			return "OK, Check";
		} else if (action.equals("bet")) {
			// Bet $25
			currentHand.setWinnings(currentHand.getWinnings() - 25);
			// Add $25 to the pot
			currentHand.getUser().getGameStates().setPot(currentHand.getUser().getGameStates().getPot() + 25);
			// Save changes to DB
			gameStatesService.updateGameState(currentHand.getUser().getGameStates());
			currentHandsService.updateCurrentHand(currentHand);
			System.out.println("User has bet!");
			System.out.println(currentHand);
			return "OK, Bet";
		} else if (action.equals("fold")) {
			// Fold Hand
			currentHand.setHasFolded(true);
			currentHandsService.updateCurrentHand(currentHand);
			System.out.println("User Has Folded!");
			System.out.println(currentHand);
			return "OK, Fold";
		}
		
		return "NO ACTION!";
		
	}

}
