package com.eduardo.communication.platform.service.communication.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.communication.platform.exception.exceptions.ResourceNotFoundException;
import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.model.Status;
import com.eduardo.communication.platform.repository.CommunicationScheduleRepository;

@Service
public class CommunicationScheduleServiceImpl implements CommunicationScheduleService {
	private final String SCHEDULE_NOT_FOUND_MESSAGE = "Schedule not found";
	
	@Autowired
	private CommunicationScheduleRepository repository;
	
	public CommunicationScheduleServiceImpl() {
		
	}
	
	@Override
	public CommunicationSchedule getSchedule(Long id) {		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SCHEDULE_NOT_FOUND_MESSAGE));
	}

	@Override
	public CommunicationSchedule registerSchedule(CommunicationSchedule schedule) {
		return repository.save(schedule);		
	}
	
	@Override
	public CommunicationSchedule updateScheduleStatus(Long id, Status newStatus) {
		CommunicationSchedule scheduleFromDB = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));
		scheduleFromDB.setStatus(newStatus);
		
		return repository.save(scheduleFromDB);
	}

	@Override
	public void removeSchedule(Long id) {
		repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SCHEDULE_NOT_FOUND_MESSAGE));
		
		repository.deleteById(id);		
	}
}
