package com.alura.wiseroom.model;

public class Event {
	String eventName;
	String eventMsg;

	public Event() {
	}

	public Event(String eventName, String eventMsg) {
		this.eventName = eventName;
		this.eventMsg = eventMsg;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getEventMsg() {
		return eventMsg;
	}

	public void setEventMsg(String eventMsg) {
		this.eventMsg = eventMsg;
	}
}