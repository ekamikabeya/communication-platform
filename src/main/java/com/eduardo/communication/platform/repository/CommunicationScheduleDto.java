package com.eduardo.communication.platform.repository;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.model.Status;

public class CommunicationScheduleDto {
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public CommunicationScheduleDto(CommunicationSchedule schedule) {
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
