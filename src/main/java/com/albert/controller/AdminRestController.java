package com.albert.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albert.model.Department;
import com.albert.model.Employee;
import com.albert.model.LeaveRequest;
import com.albert.model.MeetingRoom;
import com.albert.model.MeetingRoomRequest;
import com.albert.model.StringReturn;
import com.albert.service.AdminService;
import com.albert.model.Task;
import com.albert.model.TrainingRoom;
import com.albert.model.TrainingRoomRequest;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("admin")
public class AdminRestController {
	// auto init Repos
	@Autowired
	AdminService adminService;

	// get all employees
	@GetMapping("/emps")
	public Iterable<Employee> getAllEmp() {
		return adminService.getAllEmp();
	}

	// get all departments
	@GetMapping("/depts")
	public Iterable<Department> getAllDpt() {
		return adminService.getAllDpt();
	}


	// get all employees with specified lastName
	@GetMapping("/emps/{lname}")
	public List<Employee> getByLastName(@PathVariable("lname") String lname) {
		return adminService.getByLastName(lname);
	}
	
	// create employee and assign its depatment
	@PostMapping("/emp/create/{deptName}")
	public Employee createEmp(@RequestBody Employee emp, @PathVariable String deptName) {
		return adminService.createEmp(emp, deptName);
	}

	// create a department
	@PostMapping("/dept/create")
	public Department createDpt(@RequestBody Department dept) {
		System.out.println("here");
		return adminService.createDpt(dept);
	}
	
	// assigns task to employee
	@PostMapping("/task")
	public Task assignTask(@RequestBody Map<String, String> task) {
		return adminService.assignTask(task);
	}

	// update employee fields by id
	@PutMapping("/emp/update/{id}")
	public Employee updateEmp(@RequestBody Employee emp, @PathVariable Long id) {
		return adminService.updateEmp(emp, id);
	}

	// update department fields by id
	@PutMapping("/dept/update/{id}")
	public Department updateDpt(@RequestBody Department dept, @PathVariable Long id) {
		return adminService.updateDpt(dept, id);
	}

	// Delete department by id
	@DeleteMapping("/dept/{id}")
	public StringReturn deleteByDeptId(@PathVariable Long id) {
		return adminService.deleteByDeptId(id);
	}

	// Delete employee by id
	@DeleteMapping("/emp/{id}")
	public StringReturn deleteByEmpId(@PathVariable Long id) {
		return adminService.deleteByEmpId(id);
	}
	
	//Deny training room request
	@DeleteMapping("denyTrainingRoom/{requestId}")
	public StringReturn denyTrainingRoomRequest(@PathVariable Long requestId) {
		return adminService.denyTrainingRoomRequest(requestId);
	}
	@PutMapping("acceptTrainingRoom/{requestId}")
	public StringReturn acceptTrainingRoomRequest(@PathVariable Long requestId) {
		return adminService.acceptTrainingRoomRequest(requestId);
	}
	@PostMapping("/createTrainingRoom")
	public TrainingRoom createTrainingRoom(@RequestBody TrainingRoom tRoom) {
		return adminService.createTrainingRoom(tRoom);
	}
	@PostMapping("/createMeetingRoom")
	public MeetingRoom createMeetingRoom(@RequestBody MeetingRoom mRoom) {
		return adminService.createMeetingRoom(mRoom);
	}
	@DeleteMapping("denyMeetingRoom/{requestId}")
	public StringReturn denyMeetingRoomRequest(@PathVariable Long requestId) {
		return adminService.denyMeetingRoomRequest(requestId);
	}
	@PutMapping("/acceptMeetingRoom/{requestId}")
	public StringReturn acceptMeetingRoomRequest(@PathVariable Long requestId) {
		return adminService.acceptMeetingRoomRequest(requestId);
	}
	@GetMapping("/viewMeetings")
	public Iterable<MeetingRoom> viewMeetings() {
		return adminService.viewMeetings();
	}
	@GetMapping("/viewTrainings")
	public Iterable<TrainingRoom> viewTrainings() {
		return adminService.viewTrainings();
	}
	
	@GetMapping("/viewTrainingRequest")
	public Iterable<TrainingRoomRequest> viewTrainingRequest() {
		
		return adminService.viewTrainingRequest();
	}
	@GetMapping("/viewMeetingRequest")
	public Iterable<MeetingRoomRequest> viewMeetingRequest() {
		
		return adminService.viewMeetingRequest();
	}
	
	@GetMapping("/viewLeaveRequest")
	public Iterable<LeaveRequest> viewLeaveRequest() {
		
		return adminService.viewLeaveRequest();
	}
	@PutMapping("acceptLeave/{requestId}")
	public StringReturn acceptLeaveRequest(@PathVariable Long requestId) {
		return adminService.acceptLeaveRequest(requestId);
	}
	@DeleteMapping("denyLeave/{requestId}")
	public StringReturn denyLeaveRequest(@PathVariable Long requestId) {
		return adminService.denyLeaveRequest(requestId);
	}

}
