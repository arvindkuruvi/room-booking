package com.management.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.event.dto.EventDTO;
import com.management.event.service.ApprovalService;

@RestController
@RequestMapping("/approve/")
public class ApprovalController {
	@Autowired
	ApprovalService approvalService;

	@PostMapping("event")
	public ResponseEntity<String> approveEvent(@RequestBody EventDTO eventDto) {
		return approvalService.approveEvent(eventDto);
	}

}
