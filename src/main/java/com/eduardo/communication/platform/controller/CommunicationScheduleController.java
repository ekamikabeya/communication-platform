package com.eduardo.communication.platform.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.communication.platform.dto.request.ScheduleRequestDto;
import com.eduardo.communication.platform.dto.response.ScheduleResponseDto;
import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.service.communication.schedule.CommunicationScheduleService;

@RestController
@RequestMapping("/api")
public class CommunicationScheduleController {

	@Autowired
	private CommunicationScheduleService scheduleService;

	@GetMapping("/schedule/{id}")
	public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
		CommunicationSchedule schedule = scheduleService.getSchedule(id);

		return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
	}

	@PostMapping("/schedule")
	public ResponseEntity<CommunicationSchedule> postSchedule(@RequestBody @Valid ScheduleRequestDto scheduleDto) {
		CommunicationSchedule schedule = scheduleDto.build();
		CommunicationSchedule created = scheduleService.registerSchedule(schedule);
		
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@DeleteMapping("/schedule/{id}")
	public ResponseEntity<CommunicationSchedule> deleteSchedule(@PathVariable Long id) {
		scheduleService.removeSchedule(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

	public CommunicationScheduleController() {
	}

}
