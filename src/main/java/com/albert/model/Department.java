package com.albert.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
	private Long deptId;
	private String deptName;
	
	
	public Department() {}
	public Department(Long deptId, String deptName) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		
	}
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
	

}
