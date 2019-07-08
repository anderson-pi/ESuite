package com.albert.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Task {
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	private Long taskId;
	private String taskName;
	private String taskDesc;
	private String startDate;
	private String endDate;
	@OneToOne
	private Employee assignedTo;
	@OneToOne
	private Employee assignedBy;
	@Column(name="completed")
	private boolean status=false;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taksName) {
		this.taskName = taksName;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Employee getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Employee assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Employee getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(Employee assignedBy) {
		this.assignedBy = assignedBy;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Long getTaskId() {
		return taskId;
	}
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taksName=" + taskName + ", taskDesc=" + taskDesc + ", startDate="
				+ startDate + ", endDate=" + endDate + ", assignedTo=" + assignedTo + ", assignedBy=" + assignedBy
				+ ", status=" + status + "]";
	}
	
	
}
