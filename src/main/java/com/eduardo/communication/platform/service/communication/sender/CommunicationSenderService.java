package com.eduardo.communication.platform.service.communication.sender;

import org.springframework.stereotype.Service;

import com.eduardo.communication.platform.model.CommunicationSchedule;

@Service
public interface CommunicationSenderService {
	public void send(CommunicationSchedule schedule);
}
