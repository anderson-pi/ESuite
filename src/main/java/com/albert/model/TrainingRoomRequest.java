package com.albert.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class TrainingRoomRequest {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long requestId;
	private Long trainingRoomId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String roomDesc;
	@OneToOne()
	@JoinColumn(name="emp_id")
	private Employee empId;
	
	public TrainingRoomRequest(){}
	
	public TrainingRoomRequest(DTOTrainingRoomRequest dto){
		this.trainingRoomId = dto.getTrainingRoomId();
		this.startDate = LocalDate.parse(dto.getStartDate());
		this.endDate =LocalDate.parse(dto.getEndDate());
		this.roomDesc= dto.getRoomDesc();
	}
	
	public Long getTrainingRoomId() {
		return trainingRoomId;
	}
	public void setTrainingRoomId(Long trainingRoomId) {
		this.trainingRoomId = trainingRoomId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getRoomDesc() {
		return roomDesc;
	}
	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public Long getRequestId() {
		return requestId;
	}

	public Employee getEmpId() {
		return empId;
	}

	public void setEmpId(Employee empId) {
		this.empId = empId;
	}
	
	

}
