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
import com.eduardo.communication.platform.dto.request.ScheduleRequestDto;
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
	
	private final ResultMatcher OK_STATUS = status().isOk();
	private final ResultMatcher CREATED_STATUS = status().isCreated();
	private final ResultMatcher NO_CONTENT_STATUS = status().isNoContent();
	
	private final ResultMatcher BAD_REQUEST_STATUS = status().isBadRequest();
	private final ResultMatcher NOT_FOUND_STATUS = status().isNotFound();
	
	/*
	 * GET /api/schedule/{id}
	 * */
	
	@Test
	public void shouldReturnScheduleStatus_WhenSearchSchedule_AndItDoesExist() throws Exception {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		Long expectedId = expectedSchedule.getId();		
		
		when(scheduleService.getSchedule(expectedId)).thenReturn(expectedSchedule);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/schedule/{id}", expectedId)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(OK_STATUS)
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists());
	}
	
	@Test
	public void shouldReturnNotFound_WhenSearchSchedule_AndItDoesNotExist() throws Exception {
		Long nonExistentScheduleId = 2L;
		
	    doThrow(ResourceNotFoundException.class).when(scheduleService).getSchedule(nonExistentScheduleId);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/schedule/{id}", nonExistentScheduleId)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(NOT_FOUND_STATUS);
	}
	
	/*
	 * POST /api/schedule
	 * */
	
	@Test
	public void shouldReturnSuccess_WhenRegisterSchedule() throws Exception {
		ScheduleRequestDto requestBody = createScheduleRequestDto();		
		CommunicationSchedule expectedSchedule = createValidSchedule();
		
		when(scheduleService.registerSchedule(any(CommunicationSchedule.class))).thenReturn(expectedSchedule);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/schedule")
			      .content(asJsonString(requestBody))
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
		
		when(scheduleService.registerSchedule(expectedSchedule)).thenReturn(expectedSchedule);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/schedule")
			      .content(asJsonString(expectedSchedule))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(BAD_REQUEST_STATUS);
	}
	
	/*
	 * DELETE /api/schedule/{id}
	 * */
	
	@Test
	public void shouldRemoveSchedule_WhenRemoveSchedule_AndItDoesExist() throws Exception {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		Long expectedId = expectedSchedule.getId();	
		
		doNothing().when(scheduleService).removeSchedule(expectedId);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .delete("/api/schedule/{id}", expectedId)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(NO_CONTENT_STATUS);
	}
	
	@Test
	public void  shouldReturnNotFound_WhenRemoveSchedule_AndItDoesNotExist() throws Exception {
		Long nonExistentScheduleId = 2L;
		
		doThrow(ResourceNotFoundException.class).when(scheduleService).removeSchedule(nonExistentScheduleId);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .delete("/api/schedule/{id}", nonExistentScheduleId)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(NOT_FOUND_STATUS);
	}
	
	private static ScheduleRequestDto createScheduleRequestDto() {
		return new ScheduleRequestDto("Test Receiver", "Message", LocalDateTime.now().plusHours(1L), Status.NOT_SENT, Channel.EMAIL);
	}
	
	private static CommunicationSchedule createValidSchedule() {
		Long valudId = 1L;
		
		ScheduleRequestDto request = createScheduleRequestDto();
		CommunicationSchedule validSchedule = request.build();
		
		validSchedule.setId(valudId);
		
		return validSchedule;
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
