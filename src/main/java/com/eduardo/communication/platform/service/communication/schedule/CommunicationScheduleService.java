package com.eduardo.communication.platform.service.communication.schedule;

import com.eduardo.communication.platform.model.CommunicationSchedule;

public interface CommunicationScheduleService {
	public CommunicationSchedule getSchedule(Long id);
	public void registerSchedule(CommunicationSchedule schedule);
}
