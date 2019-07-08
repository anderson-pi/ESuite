package com.albert.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albert.dao.DeptRepo;
import com.albert.dao.EmployeeRepo;
import com.albert.dao.TaskRepo;
import com.albert.dao.UserLoginRepo;
import com.albert.model.DTOUserLogin;
import com.albert.model.Department;
import com.albert.model.Employee;
import com.albert.model.UserLogin;
import com.albert.service.JwtUserDetailsService;
import com.albert.service.EmployeeMailSender;
import com.albert.model.JwtResponse;
import com.albert.model.Task;
import com.albert.model.JwtRequest;
import com.albert.config.JwtTokenUtil;

@RestController
@RequestMapping("admin")
public class AdminRestController {
	// auto init Repos
	@Autowired
	EmployeeRepo empRepo;
	@Autowired
	DeptRepo deptRepo;
	@Autowired
	UserLoginRepo userRepo;
	@Autowired
	TaskRepo taskRepo;
	@Autowired
	EmployeeMailSender sender;



	// get all employees
	@GetMapping("/emps")
	public Iterable<Employee> getAllEmp() {
		return empRepo.findAll();
	}

	// get all departments
	@GetMapping("/depts")
	public Iterable<Department> getAllDpt() {
		return deptRepo.findAll();
	}

	// get all employees in specified department
	@GetMapping("/dept/emps/{dept}")
	public List<Employee> getFromDpt(@PathVariable("dept") String dept) {
		return empRepo.findBydept(dept);
	}

	// get name of department a employee is in
	@GetMapping("/emp/dept/{id}")
	public String getFromDpt(@PathVariable("id") Long id) {
		return empRepo.findById(id).orElse(null).getDept().getDeptName();
	}

	// get all employees with specified lastName
	@GetMapping("emps/{lname}")
	public List<Employee> getByLastName(@PathVariable("lname") String lname) {
		return empRepo.findAllBylastName(lname);
	}
	
	// create employee and assign its depatment
	@PostMapping("/emp/create/{deptName}")
	public Employee createEmp(@RequestBody Employee emp, @PathVariable String deptName) {
		Department tempDpt = deptRepo.findBydeptName(deptName);
		if (tempDpt != null) {
			emp.setDept(tempDpt);
			return empRepo.save(emp);
		}
		return new Employee();
	}

	// create a department
	@PostMapping("/dept/create")
	public Department createDpt(@RequestBody Department dept) {
		return deptRepo.save(dept);
	}
	
	// assigns task to employee
	@PostMapping("/task")
	public Task assignTask(@RequestBody Map<String, String> task) {
		Task newTask = new Task();
		newTask.setTaskName(task.get("taskName"));
		newTask.setTaskDesc(task.get("taskDesc"));
		newTask.setStartDate(task.get("startDate"));
		newTask.setEndDate(task.get("endDate"));
		newTask.setAssignedTo(empRepo.findById(Long.parseLong(task.get("assignedTo"))).orElse(null));
		newTask.setAssignedBy(empRepo.findById(Long.parseLong(task.get("assignedBy"))).orElse(null));
		if(newTask.getAssignedBy() == null || newTask.getAssignedTo() == null) {
			newTask.setTaskDesc("Employee not found");
			newTask.setTaskName("Employee not found");
			return newTask;
		}
		sender.sendingMail(newTask.getAssignedTo().getUserLogin().getUserName(),
				"Task Assignment Notification","Task Name: " + newTask.getTaskName() +"\n"+
				"Description: " + newTask.getTaskDesc() + "\n"+
				"Start Date: " + newTask.getStartDate() + "\n"+
				"End Date: " + newTask.getEndDate() + "\n\n" +
				"Please do before " + newTask.getEndDate() + "!\n\n" + 
				"Thank you,\n" + "Admin\n" + "Eoffice Corp.");
		return taskRepo.save(newTask);
	}

	// update employee fields by id
	@PutMapping("/emp/update/{id}")
	public Employee updateEmp(@RequestBody Employee emp, @PathVariable Long id) {
		Employee e = empRepo.findById(id).orElse(null);
		if (e != null) {
			emp.setEmpId(e.getEmpId());
			emp.setDept(e.getDept());
			return empRepo.save(emp);
		}
		return null;
	}

	// update department fields by id
	@PutMapping("/dept/update/{id}")
	public Department updateDpt(@RequestBody Department dept, @PathVariable Long id) {
		dept.setDeptId(id);
		return deptRepo.save(dept);
	}

	// Delete department by id
	@DeleteMapping("/dept/{id}")
	public String deleteByDeptId(@PathVariable Long id) {
		deptRepo.deleteById(id);
		return ("Deleted Department ID: " + id);
	}

	// Delete employee by id
	@DeleteMapping("/emp/{id}")
	public String deleteByEmpId(@PathVariable Long id) {
		empRepo.deleteById(id);
		return ("Deleted Employee ID: " + id);
	}
	

}
