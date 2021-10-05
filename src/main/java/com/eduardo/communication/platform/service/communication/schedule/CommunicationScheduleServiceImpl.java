package com.eduardo.communication.platform.service.communication.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.communication.platform.exception.ResourceNotFoundException;
import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.repository.CommunicationScheduleRepository;

@Service
public class CommunicationScheduleServiceImpl implements CommunicationScheduleService {

	@Autowired
	private CommunicationScheduleRepository repository;
	
	public CommunicationScheduleServiceImpl() {
		
	}
	
	@Override
	public CommunicationSchedule getSchedule(Long id) {		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
	}

	@Override
	public CommunicationSchedule registerSchedule(CommunicationSchedule schedule) {
		return repository.save(schedule);		
	}
}
