package com.albert.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albert.dao.EmployeeRepo;
import com.albert.dao.TaskRepo;
import com.albert.dao.UserLoginRepo;
import com.albert.model.Employee;
import com.albert.model.Task;
import com.albert.model.UserLogin;
import com.albert.service.EmployeeMailSender;

@RestController
@RequestMapping("emp")
public class EmployeeController {
	@Autowired
	EmployeeRepo empRepo;
	@Autowired
	UserLoginRepo userRepo;
	@Autowired
	TaskRepo taskRepo;
	@Autowired
	EmployeeMailSender sender;
	
	//gets all tasks assigned to employee
	@GetMapping("/getTasks/{empId}")
	public List<Task> getTasks(@PathVariable Long empId ){
		Optional<Employee> tempEmp = empRepo.findById(empId);
		return taskRepo.findByassignedTo(tempEmp);
		
	}
	//sets task as complete and send confirmation
	@PutMapping("/taskComplete/{taskId}")
	public String setTaskComplete(@PathVariable Long taskId) {
		Task tempTask = taskRepo.findById(taskId).orElse(null);
		if(tempTask != null) {
			tempTask.setStatus(true);
			taskRepo.save(tempTask);
			return sender.sendingMail(tempTask.getAssignedBy().getUserLogin().getUserName(),
					"Task Modification Notification",
					"Task Id: "+ tempTask.getTaskId() +" has been modified successfully please & revert if any changes.\n\n" +
					"Thank you,\n" + tempTask.getAssignedTo().getFirstName());
			
		}

		return "Task not found";
	}
	
	
	
	
}
