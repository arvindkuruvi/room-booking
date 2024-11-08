package com.management.event.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeRange {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
