package com.albert.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.albert.dao.DeptRepo;
import com.albert.dao.EmployeeRepo;
import com.albert.dao.LeaveRequestRepo;
import com.albert.dao.MeetingRoomRepo;
import com.albert.dao.MeetingRoomRequestRepo;
import com.albert.dao.TaskRepo;
import com.albert.dao.TrainingRoomRepo;
import com.albert.dao.TrainingRoomRequestRepo;
import com.albert.dao.UserLoginRepo;
import com.albert.model.Department;
import com.albert.model.Employee;
import com.albert.model.LeaveRequest;
import com.albert.model.MeetingRoom;
import com.albert.model.MeetingRoomRequest;
import com.albert.service.EmployeeMailSender;
import com.albert.model.Task;
import com.albert.model.TrainingRoom;
import com.albert.model.TrainingRoomRequest;
@CrossOrigin(origins= "http://localhost:4200")
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
	@Autowired
	TrainingRoomRequestRepo trainReqRepo;
	@Autowired
	TrainingRoomRepo trainingRoomRepo;
	@Autowired
	MeetingRoomRepo meetingRoomRepo;
	@Autowired
	MeetingRoomRequestRepo meetingReqRepo;
	@Autowired
	LeaveRequestRepo leaveReqRepo;

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
			emp.setUserLogin(e.getUserLogin());
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
	
	//Deny training room request
	@DeleteMapping("denyTrainingRoom/{requestId}")
	public String denyTrainingRoomRequest(@PathVariable Long requestId) {
		TrainingRoomRequest tempRequest = trainReqRepo.findById(requestId).orElse(null);
		trainReqRepo.deleteById(requestId);
		sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(), "Training Room Request Notification", "Request Id: " + tempRequest.getRequestId()
		+ "\nDescription: " + tempRequest.getRoomDesc() + "\n Start Date: " + tempRequest.getStartDate() 
		+ "\nEnd Date: " + tempRequest.getEndDate() + "\nThis request has been denied!\n\nThank you,\nAdmin");
		return ("Denied training room request: "+requestId);
	}
	@PutMapping("acceptTrainingRoom/{requestId}")
	public String acceptTrainingRoomRequest(@PathVariable Long requestId) {
		TrainingRoomRequest tempRequest = trainReqRepo.findById(requestId).orElse(null);
		TrainingRoom tempRoom = trainingRoomRepo.findById(tempRequest.getTrainingRoomId()).orElseThrow(null);
		LocalDate tempDate= tempRequest.getStartDate();
		while (!tempDate.equals(tempRequest.getEndDate().plusDays(1))) {
			tempRoom.getReservedDates().add(tempDate);
			tempDate = tempDate.plusDays(1);
		}
		trainingRoomRepo.save(tempRoom);
		return sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(), "Training Room Request Notification", "Request Id: " + tempRequest.getRequestId()
		+ "\nDescription: " + tempRequest.getRoomDesc() + "\n Start Date: " + tempRequest.getStartDate() 
		+ "\nEnd Date: " + tempRequest.getEndDate() + "\nThis request has been approved!\n\nThank you,\nAdmin");
		
	}
	@PostMapping("/createTrainingRoom")
	public TrainingRoom createTrainingRoom(@RequestBody TrainingRoom tRoom) {
		return trainingRoomRepo.save(tRoom);
	}
	@PostMapping("/createMeetingRoom")
	public MeetingRoom createTrainingRoom(@RequestBody MeetingRoom tRoom) {
		return meetingRoomRepo.save(tRoom);
	}
	@DeleteMapping("denyMeetingRoom/{requestId}")
	public String denyMeetingRoomRequest(@PathVariable Long requestId) {
		MeetingRoomRequest tempRequest = meetingReqRepo.findById(requestId).orElse(null);
		meetingReqRepo.deleteById(requestId);
		sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(), "Training Room Request Notification", "Request Id: " + tempRequest.getRequestId()
		+ "\nDescription: " + tempRequest.getMeetingDesc() + "\nStart Time: " + tempRequest.getStartTime() 
		+ "\nEnd Time: " +tempRequest.getEndTime()+ "\nThis request has been denied!\n\nThank you,\nAdmin");
		return ("Denied meeting room request: "+requestId);
	}
	@PutMapping("/acceptMeetingRoom/{requestId}")
	public String acceptMeetingRoomRequest(@PathVariable Long requestId) {
		MeetingRoomRequest tempRequest = meetingReqRepo.findById(requestId).orElse(null);
		MeetingRoom tempRoom = meetingRoomRepo.findById(tempRequest.getMeetingRoomId()).orElseThrow(null);
		
		LocalTime timeStart = tempRequest.getStartTime().toLocalDateTime().toLocalTime();
		LocalTime timeEnd = tempRequest.getEndTime().toLocalDateTime().toLocalTime();
		LocalDate date = tempRequest.getStartTime().toLocalDateTime().toLocalDate();
		
		Set<LocalTime> time = tempRoom.getReserved().getOrDefault(date, new HashSet<>());
		long diff = ChronoUnit.MINUTES.between(timeStart, timeEnd);
		int nbSlots = Math.abs((int)(diff/30));
		
		int  inc = 0;
		for(int i=0; i<=nbSlots; i++) {
			time.add(timeStart.plusMinutes(inc));
			inc +=30;
		}
		tempRoom.getReserved().put(date, time);
		meetingRoomRepo.save(tempRoom);
		return sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(), "Meeting Room Request Notification", "Request Id: " + tempRequest.getRequestId()
		+ "\nDescription: " + tempRequest.getMeetingDesc() + "\n Start Date: " + tempRequest.getStartTime()
		+ "\nEnd Date: " + tempRequest.getEndTime() + "\nThis request has been approved!\n\nThank you,\nAdmin");
		
	}
	@GetMapping("/viewMeetings")
	public Iterable<MeetingRoom> viewMeetings() {
		
		return meetingRoomRepo.findAll();
	}
	@GetMapping("/viewTrainings")
	public Iterable<TrainingRoom> viewTrainings() {
		
		return trainingRoomRepo.findAll();
	}
	
	@GetMapping("/viewTrainingRequest")
	public Iterable<TrainingRoomRequest> viewTrainingRequest() {
		
		return trainReqRepo.findAll();
	}
	@GetMapping("/viewMeetingRequest")
	public Iterable<MeetingRoomRequest> viewMeetingRequest() {
		
		return meetingReqRepo.findAll();
	}
	
	@GetMapping("/viewLeaveRequest")
	public Iterable<LeaveRequest> viewLeaveRequest() {
		
		return leaveReqRepo.findAll();
	}
	@PutMapping("acceptLeave/{requestId}")
	public String acceptLeaveRequest(@PathVariable Long requestId) {
		LeaveRequest acceptedRequest = leaveReqRepo.findById(requestId).orElseThrow(null);
		acceptedRequest.setStatue(true);
		leaveReqRepo.save(acceptedRequest);

		return sender.sendingMail(acceptedRequest.getEmpId().getUserLogin().getUserName(), "Leave Request Notification", "Request Id: " + acceptedRequest.getLeaveId()
		+ "\n Start Date: " + acceptedRequest.getStartDate() 
		+ "\nEnd Date: " + acceptedRequest.getEndDate() + "\nThis request has been approved!\n\nThank you,\nAdmin");
		
	}
	@PutMapping("denyLeave/{requestId}")
	public String denyLeaveRequest(@PathVariable Long requestId) {
		LeaveRequest deniedRequest = leaveReqRepo.findById(requestId).orElseThrow(null);
		leaveReqRepo.deleteById(requestId);

		return sender.sendingMail(deniedRequest.getEmpId().getUserLogin().getUserName(), "Leave Request Notification", "Request Id: " + deniedRequest.getLeaveId()
		+ "\n Start Date: " + deniedRequest.getStartDate() 
		+ "\nEnd Date: " + deniedRequest.getEndDate() + "\nThis request has been approved!\n\nThank you,\nAdmin");
		
	}

}
