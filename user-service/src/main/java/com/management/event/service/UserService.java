package com.management.event.service;

import org.springframework.http.ResponseEntity;

import com.management.event.jpa.entity.UserProfile;

public interface UserService {
	ResponseEntity<String> saveUserProfile(UserProfile userProfile);

	ResponseEntity<String> deleteUserProfile(Long profileId);

	ResponseEntity<UserProfile> getUserProfile(Long profileId);
}
