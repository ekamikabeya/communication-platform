package com.eduardo.communication.platform.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;


@Entity
@Table(name = "COMMUNICATION_SCHEDULE")
public class CommunicationSchedule {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @NotBlank
	private String receiver;
	
	@NotNull
	private String message;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@FutureOrPresent
	private LocalDateTime dateTimeToSend;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.NOT_SENT;
	
	@Enumerated(EnumType.STRING)
	private Channel channel;
	
	public CommunicationSchedule() {
		
	}
	

	public CommunicationSchedule(Long id, @NotNull @NotBlank String receiver, @NotNull String message,
			@NotNull @FutureOrPresent LocalDateTime dateTimeToSend, Status status, Channel channel) {
		super();
		this.id = id;
		this.receiver = receiver;
		this.message = message;
		this.dateTimeToSend = dateTimeToSend;
		this.status = status;
		this.channel = channel;
	}


	@Override
	public String toString() {
		return "CommunicationSchedule [id=" + id + ", receiver=" + receiver + ", message=" + message + ", dateTimeToSend="
				+ dateTimeToSend + ", status=" + status + ", channel=" + channel + "]";
	}
	
	public Long getId() {
		return id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDateTimeToSend() {
		return dateTimeToSend;
	}

	public void setDateTimeToSend(LocalDateTime dateTimeToSend) {
		this.dateTimeToSend = dateTimeToSend;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	
}
