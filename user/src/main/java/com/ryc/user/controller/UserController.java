package com.ryc.user.controller;

import com.ryc.user.model.UserModel;
import com.ryc.user.service.iface.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserModel userToCreate){
		UserModel userModelCreated = userService.createUser(userToCreate);
		return new ResponseEntity<>(userModelCreated, new HttpHeaders(), HttpStatus.CREATED);
	}
}
