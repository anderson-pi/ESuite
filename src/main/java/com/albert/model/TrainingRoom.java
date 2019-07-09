package com.albert.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TrainingRoom {
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long roomId;
	private String roomName;
	private Long roomCapacity;
	private int floorNb;
	private boolean isProjector;
	private boolean isWhiteboard;
	@ElementCollection(targetClass=LocalDate.class)
	Set<LocalDate> reservedDates = new HashSet<>();
	
	public TrainingRoom() {}

	public Long getRoomId() {
		return roomId;
	}
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(Long roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public int getFloorNb() {
		return floorNb;
	}

	public void setFloorNb(int floorNb) {
		this.floorNb = floorNb;
	}

	public boolean isProjector() {
		return isProjector;
	}

	public void setProjector(boolean isProjector) {
		this.isProjector = isProjector;
	}

	public boolean isWhiteboard() {
		return isWhiteboard;
	}

	public void setWhiteboard(boolean isWhiteboard) {
		this.isWhiteboard = isWhiteboard;
	}

	public Set<LocalDate> getReservedDates() {
		return reservedDates;
	}

	@Override
	public String toString() {
		return "TrainingRoom [roomId=" + roomId + ", roomName=" + roomName + ", reservedDates=" + reservedDates + "]\n";
	}
	
	
	
}
