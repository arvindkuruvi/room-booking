package com.management.event.service;

import org.springframework.http.ResponseEntity;

import com.management.event.dto.EventDTO;

public interface ApprovalService {
	ResponseEntity<String> approveEvent(EventDTO eventDTO);
}
