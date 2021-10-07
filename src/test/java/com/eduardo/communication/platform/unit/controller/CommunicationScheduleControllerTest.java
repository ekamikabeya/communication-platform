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
	public void shouldReturnCreated_WhenRegisterSchedule_WithValidFields() throws Exception {
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
	
	/*
	 * POST /api/schedule
	 * Testing invalid values
	 * */
	
	@Test
	public void shouldReturnError_WhenRegisterSchedule_HasInvalidSendTime() throws Exception {
		ScheduleRequestDto badRequest = new ScheduleRequestDto();		
		badRequest.setReceiver("test receiver");
		badRequest.setMessage("test message");
		
		LocalDateTime sometimeBeforeNow = LocalDateTime.now().minusDays(1L);
		badRequest.setDateTimeToSend(sometimeBeforeNow);
		
		validatePostWithBadRequest(badRequest);
	}
	
	@Test
	public void shouldReturnError_WhenRequest_HasInvalidChannel() throws Exception {
		String NOT_VALID_CHANNEL = "NOT_VALID_CHANNEL";
		
		ScheduleRequestDto badRequest = createScheduleRequestDto();
		badRequest.setChannel(NOT_VALID_CHANNEL);
		
		validatePostWithBadRequest(badRequest);
	}
	
	/*
	 * POST /api/schedule
	 * Testing missing mandatory fields
	 * */
	
	@Test
	public void shouldReturnError_WhenRequest_HasMissingFields() throws Exception {
		ScheduleRequestDto badRequest = new ScheduleRequestDto();
		
		validatePostWithBadRequest(badRequest);
	}
	
	@Test
	public void shouldReturnError_WhenRequest_IsMissingReceiver() throws Exception {
		ScheduleRequestDto requestBody = createScheduleRequestDto();
		requestBody.setReceiver(null);
		
		validatePostWithBadRequest(requestBody);
	}
	
	@Test
	public void shouldReturnError_WhenRequest_IsMissingMessage() throws Exception {
		ScheduleRequestDto requestBody = createScheduleRequestDto();
		requestBody.setMessage(null);
		
		validatePostWithBadRequest(requestBody);
	}
	
	@Test
	public void shouldReturnError_WhenRegisterSchedule_isMissingSendTime() throws Exception {
		ScheduleRequestDto requestBody = createScheduleRequestDto();
		requestBody.setDateTimeToSend(null);		
		
		validatePostWithBadRequest(requestBody);
	}
	
	@Test
	public void shouldReturnError_WhenRegisterSchedule_isMissingChannel() throws Exception {
		ScheduleRequestDto requestBody = createScheduleRequestDto();
		requestBody.setChannel(null);		
		
		validatePostWithBadRequest(requestBody);
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
	
	/*
	 * Utils
	 * */
	
	private void validatePostWithBadRequest(ScheduleRequestDto request) throws Exception {
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/schedule")
			      .content(asJsonString(request))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(BAD_REQUEST_STATUS);
	}
	
	private static ScheduleRequestDto createScheduleRequestDto() {
		return new ScheduleRequestDto("Test Receiver", "Message", LocalDateTime.now().plusHours(1L), "EMAIL");
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
