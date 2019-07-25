package com.albert.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class MeetingRoomRequest {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long requestId;
	private Long MeetingRoomId;
	private String startTime;
	private String endTime;
	private String meetingDesc;
	private boolean status;
	@OneToOne
	@JoinColumn(name="emp_id")
	private Employee empId;
	
	public MeetingRoomRequest() {}
	
	public MeetingRoomRequest(DTOMeetingRoomRequest dto) {
		this.MeetingRoomId = dto.getRoomId();
		this.startTime = Timestamp.valueOf(dto.getDay() +" "+ dto.getStartTime()).toString();
		this.endTime = Timestamp.valueOf(dto.getDay() +" "+ dto.getEndTime()).toString();
		this.meetingDesc=dto.getMeetingDesc();
		
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMeetingDesc() {
		return meetingDesc;
	}

	public void setMeetingDesc(String meetingDesc) {
		this.meetingDesc = meetingDesc;
	}

	public Employee getEmpId() {
		return empId;
	}

	public void setEmpId(Employee empId) {
		this.empId = empId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public Long getMeetingRoomId() {
		return MeetingRoomId;
	}

	public void setMeetingRoomId(Long meetingRoomId) {
		MeetingRoomId = meetingRoomId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	

}
