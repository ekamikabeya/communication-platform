package com.eduardo.communication.platform.service.communication.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.repository.CommunicationScheduleRepository;

@Service
public class CommunicationScheduleServiceImpl implements CommunicationScheduleService {

	@Autowired
	private CommunicationScheduleRepository repository;
	
	public CommunicationScheduleServiceImpl() {
		
	}

	@Override
	public void registerSchedule(CommunicationSchedule schedule) {
		repository.save(schedule);		
	}
}
