package com.revature.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.beans.GameStates;
import com.revature.service.GameStatesService;
import com.revature.service.UsersService;

import beans.Game;

@Component
public class Application {
	
	@Autowired
	private static GameStatesService gs;
	
	@Autowired
	private static UsersService us;

	public static void main(String[] args) {
		GameStates g = new GameStates(343, "", 0, 0, 0, 0,"");
		gs.deleteGameState(g);
		gs.addGameState(g);
		
		Game game = new Game(0);
	}

}
