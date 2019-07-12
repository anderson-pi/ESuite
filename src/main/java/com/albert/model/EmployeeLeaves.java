package com.albert.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EmployeeLeaves {
	@Id
	@Column(name="id")
	private Long leaveId;
	@OneToOne
	private Employee empId;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public EmployeeLeaves() {}
	public EmployeeLeaves(LeaveRequest request) {
		this.leaveId = request.getLeaveId();
		this.empId= request.getEmpId();
		this.startDate= request.getStartDate();
		this.endDate= request.getEndDate();
	}

	public Employee getEmpId() {
		return empId;
	}

	public void setEmpId(Employee empId) {
		this.empId = empId;
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

	public Long getLeaveId() {
		return leaveId;
	};
	
	

}
