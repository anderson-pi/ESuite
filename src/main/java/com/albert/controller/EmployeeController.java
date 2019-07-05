package com.albert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.albert.dao.EmployeeRepo;
import com.albert.dao.UserLoginRepo;
import com.albert.model.Employee;
import com.albert.model.UserLogin;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepo empRepo;
	@Autowired
	UserLoginRepo userRepo;
	
	
	
	
	
	
	
	
}
