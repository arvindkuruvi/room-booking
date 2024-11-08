package com.management.event.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookEventDTO {
	private Long organizerId;
	private Long roomId;
	private String purpose;
	private String startTime;
	private String endTime;
	private String eventName;
	private String eventType;
	private List<String> expectedAttendees;
}
