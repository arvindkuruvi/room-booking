package com.management.event.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.management.event.dto.ApprovalRequest;
import com.management.event.dto.EventDTO;
import com.management.event.dto.UserDTO;
import com.management.event.service.ApprovalService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	WebClient webClient;

	@Value("${user-service.baseURL}")
	private String userBaseURL;

	@Value("${user-service.urls.getUserData}")
	private String getUserDataPath;

	@Value("${booking-service.baseURL}")
	private String bookingBaseURL;

	@Value("${booking-service.urls.approveEvent}")
	private String approveEventPath;

	@Override
	public ResponseEntity<String> approveEvent(EventDTO eventDTO) {

		var url = userBaseURL + String.format(getUserDataPath, eventDTO.getUserId());

		log.info("URl :: {}", url);

		var userDTO = webClient.get().uri(url).retrieve().onStatus(status -> status.is4xxClientError(), response -> {
			return Mono.error(new RuntimeException("Bad request " + response.statusCode()));
		}).onStatus(status -> status.is5xxServerError(), response -> {
			return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
		}).bodyToMono(UserDTO.class).block();

		if (userDTO != null) {
			log.info("user details :: {}", userDTO);
			if (!userDTO.getUserType().equalsIgnoreCase("STAFF")) {
				return ResponseEntity.internalServerError().body("User doesn't have previlege to approve an event");
			}
		}

		url = bookingBaseURL + approveEventPath;

		log.info("URl :: {}", url);

		var approvalRequest = new ApprovalRequest();
		approvalRequest.setApprovedBy(eventDTO.getUserId().toString());
		approvalRequest.setIsApproved(true);
		approvalRequest.setApprovedDate(LocalDateTime.now().toString());
		approvalRequest.setEventBookingId(eventDTO.getEventBookingId());

		webClient.post().uri(url).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(approvalRequest))
				.retrieve().onStatus(status -> status.is4xxClientError(), response -> {
					return Mono.error(new RuntimeException("Bad request " + response.statusCode()));
				}).onStatus(status -> status.is5xxServerError(), response -> {
					return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
				}).bodyToMono(String.class).block();

		return ResponseEntity.ok().body("Approved event!");
	}

}
