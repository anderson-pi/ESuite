package com.albert.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeetingRoom {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long roomId;
	private String meetingRoomName;
	private Long capicity;
	private int floor;
	@ElementCollection(targetClass=HashMap.class)
	Map<LocalDate,Set<String>> reserved = new HashMap<>();
	
	public MeetingRoom() {}
	
	public Long getRoomId() {
		return roomId;
	}

	public String getMeetingRoomName() {
		return meetingRoomName;
	}

	public void setMeetingRoomName(String meetingRoomName) {
		this.meetingRoomName = meetingRoomName;
	}

	public Long getCapicity() {
		return capicity;
	}

	public void setCapicity(Long capicity) {
		this.capicity = capicity;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Map<LocalDate, Set<String>> getReserved() {
		return reserved;
	}

	@Override
	public String toString() {
		return "MeetingRoom [roomId=" + roomId + ", meetingRoomName=" + meetingRoomName + ", reserved=" + reserved
				+ "]\n";
	}
	public String print() {
		return this.toString();
	}
	
}
