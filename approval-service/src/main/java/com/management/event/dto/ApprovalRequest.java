package com.management.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequest {
	private Boolean isApproved;
	private String approvedBy;
	private String approvedDate;
	private String eventBookingId;
}
