package com.management.event.dto;

import lombok.Data;

@Data
public class RoomDTO {
	private String roomName;
	private Long capacity;
	private String features;
}
