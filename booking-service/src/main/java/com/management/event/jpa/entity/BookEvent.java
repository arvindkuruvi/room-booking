package com.management.event.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEvent {
	@MongoId(FieldType.OBJECT_ID)
	private String bookingId;
	private Long organizerId;
	private Long roomId;
	private String purpose;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String eventName;
	private String eventType;
	private String expectedAttendees;
	private Boolean isApproved;
	private String approvedBy;
	private String approvedDate;
}
