package com.eduardo.communication.platform.dto.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.model.Status;

public class ScheduleResponseDto {
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public ScheduleResponseDto() {		
	}
	
	public ScheduleResponseDto(CommunicationSchedule schedule) {
		this.id = schedule.getId();
		this.status = schedule.getStatus();
	}

	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}
	

}
