package com.eduardo.communication.platform.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eduardo.communication.platform.controller.CommunicationScheduleController;
import com.eduardo.communication.platform.exception.ResourceNotFoundException;
import com.eduardo.communication.platform.model.Channel;
import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.model.Status;
import com.eduardo.communication.platform.service.communication.schedule.CommunicationScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommunicationScheduleController.class)
public class CommunicationScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CommunicationScheduleService scheduleService;	
	
	private final ResultMatcher CREATED_STATUS = status().isCreated();
	private final ResultMatcher BAD_REQUEST_STATUS = status().isBadRequest();
	
	/*
	 * POST /api/schedule
	 * */
	
	@Test
	public void shouldReturnSuccess_WhenRegisterSchedule() throws Exception {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		doNothing().when(scheduleService).registerSchedule(expectedSchedule);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/schedule")
			      .content(asJsonString(expectedSchedule))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(CREATED_STATUS)
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());		
	}	
	
	@Test
	public void shouldReturnError_WhenRegisterSchedule_WithInvalidSendTime() throws Exception {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		LocalDateTime sometimeBeforeNow = LocalDateTime.now().minusDays(1L);
		expectedSchedule.setDateTimeToSend(sometimeBeforeNow);
		
		doNothing().when(scheduleService).registerSchedule(expectedSchedule);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/schedule")
			      .content(asJsonString(expectedSchedule))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(BAD_REQUEST_STATUS)
			      .andReturn();
	}
	
	private static CommunicationSchedule createValidSchedule() {
		return new CommunicationSchedule(1L, "Test Receiver", "Message", LocalDateTime.now().plusHours(1L), Status.NOT_SENT, Channel.EMAIL);
	}
	
	private static String asJsonString(final Object obj) {
	    try {
	    	ObjectMapper objectMapper = new ObjectMapper();

	    	objectMapper.findAndRegisterModules();

	        return objectMapper.writeValueAsString(obj);
	    } catch (Exception e) {
	    	System.out.println(e);
	        throw new RuntimeException(e);
	    }
	}
	
	
}
