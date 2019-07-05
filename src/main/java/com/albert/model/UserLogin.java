package com.albert.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

@Entity
public class UserLogin {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long uLohId;
	@Email
	private String userName;
	private String passWord;
	@OneToOne
	private Employee empId;
	
	public UserLogin(){}
	
	

	public UserLogin(@Email String eMail, String password, Employee empId) {
		super();
		this.userName = eMail;
		this.passWord = password;
		this.empId=empId;
	}


	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassWord() {
		return passWord;
	}



	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}



	public Employee getEmpId() {
		return empId;
	}



	public void setEmpId(Employee empId) {
		this.empId = empId;
	}



	public Long getuLohId() {
		return uLohId;
	}

	
	
	

}
