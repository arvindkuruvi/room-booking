package com.management.event.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.management.event.dto.ApproveDTO;
import com.management.event.dto.BookEventDTO;
import com.management.event.jpa.entity.BookEvent;

public interface BookEventService {
	ResponseEntity<String> createNewEvent(BookEventDTO request);

	ResponseEntity<String> approveEvent(ApproveDTO approveDTO);

	List<BookEvent> getUnApprovedEvents();

	List<BookEvent> getAllEvents();
}
