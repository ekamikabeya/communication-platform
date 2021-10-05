package com.eduardo.communication.platform.service.communication.schedule;

import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.model.Status;

public interface CommunicationScheduleService {
	public CommunicationSchedule getSchedule(Long id);
	public CommunicationSchedule registerSchedule(CommunicationSchedule schedule);
	public CommunicationSchedule updateScheduleStatus(Long id, Status newStatus);
	public void removeSchedule(Long id);	
}
