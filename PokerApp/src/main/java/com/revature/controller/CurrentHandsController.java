package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.revature.beans.CurrentHands;
import com.revature.beans.FullGameState;
import com.revature.beans.Users;
import com.revature.service.CurrentHandsService;
import com.revature.service.GameStatesService;
import com.revature.service.UsersService;

@Controller("currentHandsController")
@RequestMapping("/currentHands")
public class CurrentHandsController extends HttpServlet {

	@Autowired
	private CurrentHandsService currentHandsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private GameStatesService gameStatesService;

	@Autowired
	private HttpSession httpSession;

	@CrossOrigin
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
	@GetMapping("/getFullGameState/{userId}")
	@ResponseBody
	public ResponseEntity<FullGameState> getGameState(@PathVariable int userId) {
		CurrentHands user = currentHandsService.getCurrentHandById(userId);
		int gameId = user.getUser().getGameStates().getGame_Id();
		List<Users> otherplayersUsers = usersService.getUsersWithGameId(gameId);

		List<CurrentHands> otherplayers = new ArrayList<>();
		for (Users u : otherplayersUsers) {
			if (u.getuserId() != user.getUser().getuserId()) {
				otherplayers.add(currentHandsService.getCurrentHandByUsername(u.getUsername()));
			}
		}

		FullGameState game = new FullGameState(user, otherplayers, user.getUser().getGameStates().getTableState());

		return new ResponseEntity<>(game, HttpStatus.OK);
	}

	// @ResponseStatus(HttpStatus.OK)

	// @CrossOrigin
	// @RequestMapping(value = "/action/{action}", method = RequestMethod.GET)
	// @ResponseBody
	// public RedirectView userAction(@PathVariable String action,
	// HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	//// System.out.println("form params received " + formParams);
	//
	// // Get current session
	// httpSession = request.getSession(false);
	// String username = (String) httpSession.getAttribute("username");
	// CurrentHands currentHand =
	// currentHandsService.getCurrentHandByUsername(username);
	//
	//// String action = formParams.getFirst("name");
	//
	// String status = UserAction.checkUserAction(action, currentHand);
	// System.out.println(status);
	//
	// RedirectView redirectView = new RedirectView();
	// redirectView.setUrl("http://localhost:4200/gameboard");
	// return redirectView;
	// }

//	@CrossOrigin
//	@PostMapping("/act")
//	@ResponseBody
//	public RedirectView userAction(@RequestBody String action, HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("form params received " + formParams);

		// Get current session
//		httpSession = request.getSession(false);
//		String username = (String) httpSession.getAttribute("username");
//		CurrentHands currentHand = currentHandsService.getCurrentHandByUsername(username);

		// String action = formParams.getFirst("name");

//		String status = UserAction.checkUserAction(action, currentHand);
//		System.out.println(status);

//		RedirectView redirectView = new RedirectView();
//		redirectView.setUrl("http://localhost:4200/gameboard");
//		return redirectView;
//	}
	
	@CrossOrigin
	@RequestMapping("/action/{action}")
	@ResponseBody
    public RedirectView logout(@PathVariable String action, HttpSession session) {
        System.out.println(action);
        
		String username = (String) session.getAttribute("username");
		CurrentHands currentHand = currentHandsService.getCurrentHandByUsername(username);

        System.out.println(currentHand);
        
		// Increment Player Order
		currentHand.getUser().getGameStates().setCurrentTurn(currentHand.getPlayerOrder() + 1);

		if (action.equals("check")) {
			// Do nothing
			// Save changes to DB
			gameStatesService.updateGameState(currentHand.getUser().getGameStates());
			System.out.println(currentHand.getUser().getGameStates().getCurrentTurn());
			System.out.println("User has checked!");
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
		} else if (action.equals("fold")) {
			// Fold Hand
			currentHand.setHasFolded(true);
			currentHandsService.updateCurrentHand(currentHand);
			System.out.println("User Has Folded!");
			System.out.println(currentHand);
		}

        RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:4200/gameboard/");
		return redirectView;
    }
}
