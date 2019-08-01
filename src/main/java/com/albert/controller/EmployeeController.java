package com.albert.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albert.model.DTOLeaveRequest;
import com.albert.model.DTOMeetingRoomRequest;
import com.albert.model.DTOTrainingRoomRequest;
import com.albert.model.Employee;
import com.albert.model.LeaveRequest;
import com.albert.model.MeetingRoomRequest;
import com.albert.model.StringReturn;
import com.albert.model.Task;
import com.albert.model.TrainingRoomRequest;
import com.albert.service.EmployeeService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("emp")
public class EmployeeController {

	@Autowired
	EmployeeService empService;
	
//	@GetMapping("/weather")
//	public HttpResponse<JsonNode> getWeather() throws UnirestException {
//		return empService.getWeather();
//	}
	
	//gets all tasks assigned to employee
	@GetMapping("/getTasks/{empId}")
	public ArrayList<Task> getTasks(@PathVariable Long empId ){
		return empService.getTasks(empId);
	}
	//sets task as complete and send confirmation
	@PutMapping("/taskComplete")
	public StringReturn setTaskComplete(@RequestBody Long taskId) {
		return empService.setTaskComplete(taskId);
	}
	
	//request a training room
	@PostMapping("/trainingRoom/{empId}")
	public StringReturn requestTrainingRoom(@RequestBody DTOTrainingRoomRequest dto,@PathVariable Long empId) {
		return empService.requestTrainingRoom(dto, empId);
	}
	@PostMapping("/meetingRoom/{empId}")
	public StringReturn requestMeetingRoom(@RequestBody DTOMeetingRoomRequest dto,@PathVariable Long empId) {
		return empService.requestMeetingRoom(dto, empId);
		
	}
	
	@PostMapping("/leaveRequest/{empId}")
	public StringReturn requestLeave(@RequestBody DTOLeaveRequest dto, @PathVariable Long empId) {
		return empService.requestLeave(dto, empId);
	}
	
	@GetMapping("/viewLeaveRequests/{empId}")
	public List<LeaveRequest> viewLeaveRequest(@PathVariable Long empId) {
		return empService.viewLeaveRequest(empId);
		
	}
	@GetMapping("/viewMeetingRequests/{empId}")
	public List<MeetingRoomRequest> viewMeetingRequest(@PathVariable Long empId) {
		return empService.viewMeetingRequest(empId);
	}
	@GetMapping("/viewTrainingRequests/{empId}")
	public List<TrainingRoomRequest> viewTrainingRequest(@PathVariable Long empId) {
		return empService.viewTrainingRequest(empId);
		 
	}
	@GetMapping("/findUsersId/{user}")
	public Employee findUsersID(@PathVariable String user){
		return empService.findUsersID(user);
	}
	
	@GetMapping("/findUsersRole/{user}")
	public StringReturn findUsersRole(@PathVariable String user){
		return empService.findUsersRole(user);
	}
	
	
	
}
