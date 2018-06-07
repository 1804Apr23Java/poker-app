package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.beans.CurrentHands;
import com.revature.beans.FullGameState;
import com.revature.beans.GameStates;
import com.revature.beans.Users;
import com.revature.service.CurrentHandsService;
import com.revature.service.UsersService;

@Controller("currentHandsController")
@RequestMapping("/currentHands")
public class CurrentHandsController {

	@Autowired
	private CurrentHandsService currentHandsService;
	
	@Autowired
	private UsersService usersService;

	@GetMapping("/all")
	@ResponseBody
	public ResponseEntity<List<CurrentHands>> getAllUsers() {
		return new ResponseEntity<>(currentHandsService.getCurrentHands(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/byUsername/{username}", method = RequestMethod.GET)
	public ResponseEntity<CurrentHands> getStatsByUser(@PathVariable String username) {
		ResponseEntity<CurrentHands> resp = null;
		CurrentHands currentHand = currentHandsService.getCurrentHandByUsername(username);
		resp = new ResponseEntity<>(currentHand, HttpStatus.OK);
		return resp;
	}
	
	@CrossOrigin
	@GetMapping("/getFullGameState/{i}")
	@ResponseBody
	public ResponseEntity<FullGameState> getGameState(@PathVariable int i) {	
		String[] tableStates = {
			"", //0
			"", //1
			"", //2
			"", //3
			"AH QS KD", //4
			"AH QS KD", //5
			"AH QS KD", //6
			"AH QS KD", //7
			"AH QS KD JS", //8
			"AH QS KD JS", //9
			"AH QS KD JS", //10
			"AH QS KD JS 9H", //11
		};
		
		GameStates[] g = { 
				new GameStates("", 0, 0, 0, 0, tableStates[i]), //0
				new GameStates("", 0, 250, 0, 0, tableStates[i]), //1
				new GameStates("", 0, 250, 0, 0, tableStates[i]), //2
				new GameStates("", 0, 500, 0, 0, tableStates[i]), //3
				new GameStates("", 0, 750, 0, 0, tableStates[i]), //4
				new GameStates("", 0, 1250, 0, 0, tableStates[i]), //5
				new GameStates("", 0, 1250, 0, 0, tableStates[i]), //6
				new GameStates("", 0, 1250, 0, 0, tableStates[i]), //7
				new GameStates("", 0, 1250, 0, 0, tableStates[i]), //8
				new GameStates("", 0, 1250, 0, 0, tableStates[i]), //9
				new GameStates("", 0, 1250, 0, 0, tableStates[i]), //10
				new GameStates("", 0, 1450, 0, 0, tableStates[i]) //11
		};
		
		Users kevin = new Users(0, g[i], "", "", "", "", "kMagno", false);
		Users ian = new Users(0, g[i], "", "", "", "", "iDrumm", false);
		Users jj = new Users(0, g[i], "", "", "", "", "jjMbng", false);
		Users angela = new Users(0, g[i], "", "", "", "", "aWang", false);
		
		CurrentHands[] kevinHand = {
				new CurrentHands(kevin, "AC 3H", 1000, false, 0), //0
				new CurrentHands(kevin, "AC 3H", 750, false, 0), //1
				new CurrentHands(kevin, "AC 3H", 750, false, 0), //2
				new CurrentHands(kevin, "AC 3H", 750, false, 0), //3
				new CurrentHands(kevin, "AC 3H", 750, false, 0), //4
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //5
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //6
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //7
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //8
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //9
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //10
				new CurrentHands(kevin, "AC 3H", 250, false, 0), //11
		};
		
		CurrentHands[][] currHands = {
				{new CurrentHands(ian, "BC BC", 1000, false, 1),
				 new CurrentHands(jj, "BC BC", 1000, false, 1),
				 new CurrentHands(angela, "BC BC", 1000,false, 1)}, //0
				
				{new CurrentHands(ian, "CC CC", 1000, false, 1),
					 new CurrentHands(jj, "BC BC", 1000, false, 1),
					 new CurrentHands(angela, "BC BC", 1000,false, 1)}, //1
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
					 new CurrentHands(jj, "CC CC", 1000, false, 1),
					 new CurrentHands(angela, "BC BC", 1000,false, 1)}, //2
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
					 new CurrentHands(jj, "BC BC", 750, false, 1),
					 new CurrentHands(angela, "CC CC", 1000,false, 1)}, //3
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
					 new CurrentHands(jj, "BC BC", 750, false, 1),
					 new CurrentHands(angela, "BC BC", 750,false, 1)}, //4
				
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
					 new CurrentHands(jj, "CC CC", 750, false, 1),
					 new CurrentHands(angela, "BC BC", 750,false, 1)}, //5
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
				  new CurrentHands(jj, "GC GC", 750, false, 1),
				  new CurrentHands(angela, "CC CC", 750,false, 1)}, //6
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
				  new CurrentHands(jj, "GC GC", 750, false, 1),
				  new CurrentHands(angela, "BC BC", 250,false, 1)}, //7
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
				  new CurrentHands(jj, "GC GC", 750, false, 1),
				  new CurrentHands(angela, "AC AC", 250,false, 1)}, //8
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
					  new CurrentHands(jj, "GC GC", 750, false, 1),
					  new CurrentHands(angela, "BC BC", 250,false, 1)}, //9
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
						  new CurrentHands(jj, "GC GC", 750, false, 1),
						  new CurrentHands(angela, "AC AC", 250,false, 1)}, //10
				 
				 {new CurrentHands(ian, "GC GC", 1000, false, 1),
							  new CurrentHands(jj, "GC GC", 750, false, 1),
							  new CurrentHands(angela, "BC BC", 50,false, 1)}, //10
					 
		};
		
		ArrayList<CurrentHands> list = new ArrayList<>();
		for(CurrentHands c : currHands[i]) {
			list.add(c);
		}
		
		FullGameState game = new FullGameState(kevinHand[i], list,  tableStates[i]);
			
		return new ResponseEntity<>(game, HttpStatus.OK);
	}
}
