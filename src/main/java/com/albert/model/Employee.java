package com.albert.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Employee")
public class Employee {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long empId;
	private String firstName;
	private String lastName;
	@Email
	private String eMail; //valid email
	private String contactNo;
	@ManyToOne
	@JoinColumn(name="dept_id")
	private Department dept; //foreignKey , 
	
	public Employee() {}
	public Employee(Long empId, String firstName, String lastName, String eMail, String contactNo, Department dept) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.contactNo = contactNo;
		this.dept = dept;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
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
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
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
	
	
	
}
