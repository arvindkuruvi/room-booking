package com.management.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.event.constants.Constants;
import com.management.event.jpa.entity.UserProfile;
import com.management.event.jpa.repository.UserProfileRepository;
import com.management.event.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserProfileRepository userProfileRepository;

	@Override
	public ResponseEntity<String> saveUserProfile(UserProfile userProfile) {
		log.info("Initializing saving profile...");
		if (userProfileRepository.countByEmail(userProfile.getEmail()) > 0) {
			log.info(Constants.EMAIL_ALREADY_EXISTS);
			return ResponseEntity.badRequest().body(Constants.EMAIL_ALREADY_EXISTS);
		} else {
			if (userProfile.getUserType().equalsIgnoreCase("student")) {
				userProfile.setUserType("STUDENT");
			} else if (userProfile.getUserType().equalsIgnoreCase("staff")) {
				userProfile.setUserType("STAFF");
			} else if (userProfile.getUserType().equalsIgnoreCase("faculty")) {
				userProfile.setUserType("FACULTY");
			} else {
				return ResponseEntity.internalServerError().body(Constants.UNKNOWN_USER_TYPE);
			}

			userProfile = userProfileRepository.save(userProfile);
			log.info("Profile saved!");
		}
		return ResponseEntity.ok().body(Constants.PROFILE_SAVED_SUCCESS + userProfile.getProfileId());
	}

	@Override
	public ResponseEntity<String> deleteUserProfile(Long profileId) {
		log.info("Initializing deleting profile...");
		if (userProfileRepository.countByProfileId(profileId) > 0) {
			userProfileRepository.deleteById(profileId);
			log.info("Profile deleted for id :: {}", profileId);
			return ResponseEntity.ok().body(Constants.PROFILE_DELETE_SUCCESS);
		}
		log.info(Constants.PROFILE_NOT_FOUND);
		return ResponseEntity.badRequest().body(Constants.PROFILE_NOT_FOUND);
	}

	@Override
	public ResponseEntity<UserProfile> getUserProfile(Long profileId) {
		log.info("Initializing retrieving profile...");
		var userOptional = userProfileRepository.findById(profileId);
		if (userOptional.isPresent()) {
			log.info("Profile found for id :: {}", profileId);
			return ResponseEntity.ok().body(userOptional.get());
		}
		log.info(Constants.PROFILE_NOT_FOUND);
		return ResponseEntity.internalServerError().body(new UserProfile());
	}
}
