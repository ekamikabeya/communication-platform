package com.eduardo.communication.platform.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eduardo.communication.platform.exception.ResourceNotFoundException;
import com.eduardo.communication.platform.model.Channel;
import com.eduardo.communication.platform.model.CommunicationSchedule;
import com.eduardo.communication.platform.model.Status;
import com.eduardo.communication.platform.repository.CommunicationScheduleRepository;
import com.eduardo.communication.platform.service.communication.schedule.CommunicationScheduleServiceImpl;

@ExtendWith(SpringExtension.class)
public class CommunicationScheduleServiceTest {
	@Mock
    private CommunicationScheduleRepository repository;
    
	@InjectMocks
	private CommunicationScheduleServiceImpl service;	
	
	@Test
	public void shouldReturnSchedule_WhenGetSchedule_AndItExists() {		
		CommunicationSchedule expectedSchedule = createValidSchedule();
		Long expectedId = expectedSchedule.getId();		
		
		when(repository.findById(expectedId)).thenReturn(Optional.of(expectedSchedule));
		
		CommunicationSchedule actualSchedule = service.getSchedule(expectedId);
		
		assertEquals(expectedId, actualSchedule.getId());
	}
	
	@Test
	public void shouldThrowError_WhenGetSchedule_AndItDoesNotExist() {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		Long expectedId = expectedSchedule.getId();		
		
		when(repository.findById(expectedId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.getSchedule(expectedId);
		});
	}
	
	@Test
	public void registerSchedule() {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		
		service.registerSchedule(expectedSchedule);
		
		verify(repository).save(expectedSchedule);
	}	

	@Test
	public void updateScheduleStatus_WhenIdExists() {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		Long expectedId = expectedSchedule.getId();		
		Status updatedStatus = Status.SENT;
		
		when(repository.findById(expectedId)).thenReturn(Optional.of(expectedSchedule));
		when(repository.save(expectedSchedule)).thenReturn(expectedSchedule);
		
		CommunicationSchedule actual = service.updateScheduleStatus(expectedId, updatedStatus);
		
		assertEquals(expectedId, actual.getId());
		assertEquals(updatedStatus, actual.getStatus());
	}
	
	@Test
	public void updateScheduleStatus_WhenIdDoesNotExist() {
		Long idNotPresent = 99L;
		
		when(repository.findById(idNotPresent)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.updateScheduleStatus(idNotPresent, any(Status.class));
		});
	}
	
	@Test
	public void removeSchedule_WhenIdExists() {
		CommunicationSchedule expectedSchedule = createValidSchedule();
		Long expectedId = expectedSchedule.getId();			
		when(repository.findById(expectedId)).thenReturn(Optional.of(expectedSchedule));
		
		service.removeSchedule(expectedId);
		
		verify(repository).deleteById(expectedId);
	}
	
	@Test
	public void removeSchedule_WhenIdDoesNotExist() {
		Long idNotPresent = 99L;
		
		when(repository.findById(idNotPresent)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.removeSchedule(idNotPresent);
		});		
		
		verify(repository, never()).deleteById(idNotPresent);
	}
	
	private static CommunicationSchedule createValidSchedule() {
		return new CommunicationSchedule("Test Receiver", "Message", LocalDateTime.now().plusHours(1L), Channel.EMAIL);
	}
}
