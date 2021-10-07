package com.eduardo.communication.platform.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Channel {
    @JsonProperty("EMAIL")
	EMAIL,
	@JsonProperty("SMS")
	SMS,
	@JsonProperty("PUSH")
	PUSH,
	@JsonProperty("WHATSAPP")
	WHATSAPP;
	
    public static Channel getChannelFromString(String value){
    	try {
    		return valueOf(value);
    	} catch (IllegalArgumentException ex) {
    		throw new IllegalArgumentException("Invalid channel: " + value + ". Expected " + Arrays.toString(values()));
    	}
    }
}
