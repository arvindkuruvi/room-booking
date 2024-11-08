package com.management.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.event.jpa.entity.UserProfile;
import com.management.event.service.UserService;

@RestController
@RequestMapping("/users/")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("save")
	public ResponseEntity<String> saveUser(@RequestBody UserProfile userProfile) {
		return userService.saveUserProfile(userProfile);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long profileId) {
		return userService.deleteUserProfile(profileId);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserProfile> getUser(@PathVariable("id") long profileId) {
		return userService.getUserProfile(profileId);
	}
}
