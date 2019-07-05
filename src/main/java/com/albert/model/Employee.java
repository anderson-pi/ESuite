package com.albert.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Employee")
public class Employee {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long empId;
	private String firstName;
	private String lastName;
	private String contactNo;
	private String role;
	@ManyToOne
	@JoinColumn(name="dept_id")
	private Department dept; //foreignKey , 
	
	public Employee() {}
	public Employee(Long empId, String firstName, String lastName, String contactNo, String role, Department dept) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.role=role;
		this.dept = dept;
	}
	public void setEmpId(long empId) {
		this.empId=empId;
	}
	public Long getEmpId() {
		return empId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public Department getDept() {
		return dept;
	}
	@JsonIgnore
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
