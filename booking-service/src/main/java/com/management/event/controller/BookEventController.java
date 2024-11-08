package com.management.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.event.dto.ApproveDTO;
import com.management.event.dto.BookEventDTO;
import com.management.event.jpa.entity.BookEvent;
import com.management.event.service.BookEventService;

@RestController
@RequestMapping("/booking/")
public class BookEventController {
	@Autowired
	BookEventService bookEventService;

	@PostMapping("event")
	public ResponseEntity<String> createEvent(@RequestBody BookEventDTO bookEventDTO) {
		return bookEventService.createNewEvent(bookEventDTO);
	}

	@PostMapping("approve")
	public ResponseEntity<String> approveEvent(@RequestBody ApproveDTO approveDTO) {
		return bookEventService.approveEvent(approveDTO);
	}

	@GetMapping("unapproved")
	public List<BookEvent> unapprovedEvents() {
		return bookEventService.getUnApprovedEvents();
	}

	@GetMapping("events")
	public List<BookEvent> allEvents() {
		return bookEventService.getAllEvents();
	}
}
