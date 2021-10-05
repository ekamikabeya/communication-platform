package com.eduardo.communication.platform.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.service.communication.schedule.CommunicationScheduleService;

@RestController
@RequestMapping("/api")
public class CommunicationScheduleController {
	
	@Autowired
	private CommunicationScheduleService scheduleService;
	
	
	@PostMapping("/schedule")
	public ResponseEntity<CommunicationSchedule> postSchedule(@RequestBody @Valid CommunicationSchedule schedule) {
		scheduleService.registerSchedule(schedule);
		
		return new ResponseEntity<>(schedule, HttpStatus.CREATED);
	}
	
	public CommunicationScheduleController() {
	}

}
