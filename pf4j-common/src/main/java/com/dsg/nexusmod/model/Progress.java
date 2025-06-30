package com.dsg.nexusmod.model;

public class Progress {

	private int value = 0;
	private String message = "";
	
	public Progress(int value, String message) {
		super();
		this.value = value;
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
