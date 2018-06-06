package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.revature.beans.Users;
import com.revature.requestHelper.UserLogin;
import com.revature.service.UsersService;

@Controller("usersController")
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@CrossOrigin
	@GetMapping("/all")
	@ResponseBody
	public ResponseEntity<List<Users>> getAllUsers() {
		return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
	}

	// test path for HTML page
	@GetMapping(value = "/HelloPage")
	public String getStaticHelloPage() {
		return "Hello";
	}
	
	@CrossOrigin
	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RedirectView handleLogin(@RequestBody MultiValueMap<String, String> formParams) {
		System.out.println("form params received " + formParams);

		String username = formParams.getFirst("username");
		String password = formParams.getFirst("password");

		System.out.println(username + " is trying to login with password:" + password);

		Users user = usersService.getUserByUsername(username);
		System.out.println(user);
		
		String dest = UserLogin.checkLogin(user, password);
		
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(dest);
		System.out.println(dest);
		return redirectView;
	}

	// to return bundled Angular app
	@GetMapping(value = "/app")
	public String getApp() {
		return "forward:/static/index.html";
	}

	// for delivering our static HTML pages
	@GetMapping(value = "/staticFlashcard")
	public String getStaticFlashcardPage() {
		return "forward:/static/staticFlashcard.html";
	}

}
