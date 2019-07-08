package com.albert.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
public class UserLogin {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	@Email
	private String userName;
	private String passWord;
	private String role;
	
	public UserLogin(){}
	
	

	public UserLogin(@Email String eMail, String password, Employee empId) {
		super();
		this.userName = eMail;
		this.passWord = password;
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


	public Long getUserId() {
		return userId;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	

}
