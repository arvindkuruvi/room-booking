package com.management.event.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.management.event.constants.Constants;
import com.management.event.dto.ApproveDTO;
import com.management.event.dto.BookEventDTO;
import com.management.event.dto.RoomDTO;
import com.management.event.dto.TimeRange;
import com.management.event.jpa.entity.BookEvent;
import com.management.event.repository.BookEventRepository;
import com.management.event.service.BookEventService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BookEventServiceImpl implements BookEventService {

	@Autowired
	BookEventRepository bookEventRepository;

	@Value("${room-service.url}")
	private String roomServiceURL;

	@Value("${room-service.services.getRoomdata}")
	private String getRoomdataURL;

	@Autowired
	WebClient webClient;

	@Override
	public ResponseEntity<String> createNewEvent(BookEventDTO request) {

		List<BookEvent> listOfEvents = bookEventRepository.findAllByRoomId(request.getRoomId());

		var startTime = request.getStartTime().replace(" ", "T");
		var endTime = request.getEndTime().replace(" ", "T");

		log.info("start ::{}", startTime);
		log.info("endTime ::{}", endTime);

		var formattedStartTime = LocalDateTime.parse(startTime);
		var formattedEndTime = LocalDateTime.parse(endTime);

		if (listOfEvents.size() > 0) {
			List<TimeRange> timeRanges = listOfEvents.stream()
					.map(bookEvent -> new TimeRange(bookEvent.getStartTime(), bookEvent.getEndTime())).toList();

			boolean isRoomAvailable = this.isTimeRangeAvailable(timeRanges, formattedStartTime, formattedEndTime);

			log.info("isRoomAvailable :: {}", isRoomAvailable);

			if (!isRoomAvailable)
				return ResponseEntity.badRequest().body(Constants.ROOM_NOT_AVAILABLE);
		}

		var url = roomServiceURL + String.format(getRoomdataURL, request.getRoomId());

		log.info("URl :: {}", url);

		RoomDTO roomDTO = webClient.get().uri(url).retrieve()
				.onStatus(status -> status.is4xxClientError(), response -> {
					return Mono.error(new RuntimeException(" " + response.statusCode()));
				}).onStatus(status -> status.is5xxServerError(), response -> {
					log.info("error :: {}", response);
					return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
				}).bodyToMono(RoomDTO.class).block();

		if (roomDTO != null) {
			log.info("Room details :: {}", roomDTO);
		} else
			return ResponseEntity.badRequest().body(Constants.ROOM_NOT_PRESENT + request.getRoomId());

		var bookEvent = new BookEvent();
		BeanUtils.copyProperties(request, bookEvent);
		bookEvent.setEndTime(formattedEndTime);
		bookEvent.setStartTime(formattedStartTime);
		bookEvent.setIsApproved(false);
		if (request.getExpectedAttendees() != null) {
			bookEvent.setExpectedAttendees(String.join(", ", request.getExpectedAttendees()));
		}

		bookEvent = bookEventRepository.save(bookEvent);

		log.info(Constants.EVENT_CREATE_SUCCESS);

		return ResponseEntity.ok().body(Constants.EVENT_CREATE_SUCCESS + bookEvent.getBookingId());
	}

	public boolean isTimeRangeAvailable(List<TimeRange> existingRanges, LocalDateTime newStartDateTime,
			LocalDateTime newEndDateTime) {
		// Check if the new time range overlaps with any existing range
		for (TimeRange range : existingRanges) {
			LocalDateTime existingStart = range.getStartTime();
			LocalDateTime existingEnd = range.getEndTime();

			// If the new time range overlaps with any existing range, return false
			if ((newStartDateTime.isBefore(existingEnd) && newEndDateTime.isAfter(existingStart))
					|| (newStartDateTime.isEqual(existingStart) || newEndDateTime.isEqual(existingEnd))) {
				return false; // overlap found
			}
		}
		return true; // no overlap found
	}

	@Override
	public ResponseEntity<String> approveEvent(ApproveDTO approveDTO) {

		var bookingOptional = bookEventRepository.findById(approveDTO.getEventBookingId());
		if (bookingOptional.isEmpty())
			return ResponseEntity.badRequest().body("No event found");

		var bookingEvent = bookingOptional.get();

		BeanUtils.copyProperties(approveDTO, bookingEvent);
		bookEventRepository.save(bookingEvent);

		return ResponseEntity.ok().body("Event updated successfully");
	}

	@Override
	public List<BookEvent> getUnApprovedEvents() {
		return getAllEvents().parallelStream().filter(bookEvent -> !bookEvent.getIsApproved()).toList();
	}

	@Override
	public List<BookEvent> getAllEvents() {
		return bookEventRepository.findAll();
	}
}
