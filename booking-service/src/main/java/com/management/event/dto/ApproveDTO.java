package com.management.event.dto;

import lombok.Data;

@Data
public class ApproveDTO {
	private Boolean isApproved;
	private String approvedBy;
	private String approvedDate;
	private String eventBookingId;
}
