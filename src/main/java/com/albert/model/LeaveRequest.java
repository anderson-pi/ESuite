package com.albert.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LeaveRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long leaveId;
	private String leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;
	private boolean statue = false;
	@OneToOne
	private Employee empId;

	public LeaveRequest() {
	}

	public LeaveRequest(String leaveType, LocalDate startDate, LocalDate endDate, String reason) {
		super();
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
	}

	public LeaveRequest(DTOLeaveRequest dto) {
		super();
		this.leaveType = dto.getLeaveType();
		this.startDate = LocalDate.parse(dto.getStartDate());
		this.endDate = LocalDate.parse(dto.getEndDate());
		this.reason = dto.getReason();
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public Employee getEmpId() {
		return empId;
	}

	public void setEmpId(Employee empId) {
		this.empId = empId;
	}

	public boolean isStatue() {
		return statue;
	}

	public void setStatue(boolean statue) {
		this.statue = statue;
	}
	

}
