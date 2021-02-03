package com.ryc.user.controller;

import com.ryc.user.model.UserModel;
import com.ryc.user.service.iface.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> findUser(@PathVariable String email){
		UserModel userModel = userService.findUser(email);
		return new ResponseEntity<>(userModel, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserModel>> getAllUsers(){
		List<UserModel> userModels = userService.findAllUsers();
		return new ResponseEntity<>(userModels, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserModel userToCreate){
		UserModel userModelCreated = userService.createUser(userToCreate);
		return new ResponseEntity<>(userModelCreated, new HttpHeaders(), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{email}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> updateUser(@PathVariable String email, @RequestBody @Valid UserModel userToUpdate){
		UserModel userModelCreated = userService.updateUser(email, userToUpdate);
		return new ResponseEntity<>(userModelCreated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{email}")
	public ResponseEntity deleteUser(@PathVariable String email){
		userService.deleteUser(email);
		return new ResponseEntity(new HttpHeaders(), HttpStatus.NO_CONTENT);
	}


}
