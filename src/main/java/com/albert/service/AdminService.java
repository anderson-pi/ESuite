package com.albert.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.albert.model.StringReturn;
import com.albert.service.EmployeeMailSender;
import com.albert.model.Task;
import com.albert.model.TrainingRoom;
import com.albert.model.TrainingRoomRequest;

@Service
public class AdminService {

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

	public Iterable<Employee> getAllEmp() {
		return empRepo.findAll();
	}

	// get all departments

	public Iterable<Department> getAllDpt() {
		return deptRepo.findAll();
	}

	// get all employees with specified lastName

	public List<Employee> getByLastName(String lname) {
		return empRepo.findAllBylastName(lname);
	}

	// create employee and assign its depatment

	public Employee createEmp(Employee emp, String deptName) {
		Department tempDpt = deptRepo.findBydeptName(deptName);
		if (tempDpt != null) {
			emp.setDept(tempDpt);
			return empRepo.save(emp);
		}
		return new Employee();
	}

	// create a department

	public Department createDpt(Department dept) {
		return deptRepo.save(dept);
	}

	// assigns task to employee

	public Task assignTask(Map<String, String> task) {
		Task newTask = new Task();
		newTask.setTaskName(task.get("taskName"));
		newTask.setTaskDesc(task.get("taskDesc"));
		newTask.setStartDate(task.get("startDate"));
		newTask.setEndDate(task.get("endDate"));
		newTask.setAssignedTo(empRepo.findById(Long.parseLong(task.get("assignedTo"))).orElse(null));
		newTask.setAssignedBy(empRepo.findById(Long.parseLong(task.get("assignedBy"))).orElse(null));
		if (newTask.getAssignedBy() == null || newTask.getAssignedTo() == null) {
			newTask.setTaskDesc("Employee not found");
			newTask.setTaskName("Employee not found");
			return newTask;
		}
		sender.sendingMail(newTask.getAssignedTo().getUserLogin().getUserName(), "Task Assignment Notification",
				"Task Name: " + newTask.getTaskName() + "\n" + "Description: " + newTask.getTaskDesc() + "\n"
						+ "Start Date: " + newTask.getStartDate() + "\n" + "End Date: " + newTask.getEndDate() + "\n\n"
						+ "Please do before " + newTask.getEndDate() + "!\n\n" + "Thank you,\n" + "Admin\n"
						+ "Eoffice Corp.");
		return taskRepo.save(newTask);
	}

	// update employee fields by id

	public Employee updateEmp(Employee emp, Long id) {
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

	public Department updateDpt(Department dept, Long id) {
		dept.setDeptId(id);
		return deptRepo.save(dept);
	}

	// Delete department by id

	public StringReturn deleteByDeptId(Long id) {
		deptRepo.deleteById(id);
		return new StringReturn("Deleted Department ID: " + id);
	}

	// Delete employee by id

	public StringReturn deleteByEmpId(Long id) {
		empRepo.deleteById(id);
		return new StringReturn("Deleted Employee ID: " + id);
	}

	// Deny training room request

	public StringReturn denyTrainingRoomRequest(Long requestId) {
		TrainingRoomRequest tempRequest = trainReqRepo.findById(requestId).orElse(null);
		trainReqRepo.deleteById(requestId);
		sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(), "Training Room Request Notification",
				"Request Id: " + tempRequest.getRequestId() + "\nDescription: " + tempRequest.getRoomDesc()
						+ "\n Start Date: " + tempRequest.getStartDate() + "\nEnd Date: " + tempRequest.getEndDate()
						+ "\nThis request has been denied!\n\nThank you,\nAdmin");
		return new StringReturn("Denied training room request: " + requestId);
	}

	public StringReturn acceptTrainingRoomRequest(Long requestId) {
		TrainingRoomRequest tempRequest = trainReqRepo.findById(requestId).orElse(null);
		tempRequest.setStatus(true);
		TrainingRoom tempRoom = trainingRoomRepo.findById(tempRequest.getTrainingRoomId()).orElseThrow(null);
		LocalDate tempDate = tempRequest.getStartDate();
		while (!tempDate.equals(tempRequest.getEndDate().plusDays(1))) {
			tempRoom.getReservedDates().add(tempDate);
			tempDate = tempDate.plusDays(1);
		}
		trainReqRepo.save(tempRequest);
		trainingRoomRepo.save(tempRoom);
		return new StringReturn(sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(),
				"Training Room Request Notification",
				"Request Id: " + tempRequest.getRequestId() + "\nDescription: " + tempRequest.getRoomDesc()
						+ "\n Start Date: " + tempRequest.getStartDate() + "\nEnd Date: " + tempRequest.getEndDate()
						+ "\nThis request has been approved!\n\nThank you,\nAdmin"));

	}

	public TrainingRoom createTrainingRoom(TrainingRoom tRoom) {
		return trainingRoomRepo.save(tRoom);
	}

	public MeetingRoom createMeetingRoom(MeetingRoom mRoom) {
		return meetingRoomRepo.save(mRoom);
	}

	public StringReturn denyMeetingRoomRequest(Long requestId) {
		MeetingRoomRequest tempRequest = meetingReqRepo.findById(requestId).orElse(null);
		meetingReqRepo.deleteById(requestId);
		sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(), "Training Room Request Notification",
				"Request Id: " + tempRequest.getRequestId() + "\nDescription: " + tempRequest.getMeetingDesc()
						+ "\nStart Time: " + tempRequest.getStartTime() + "\nEnd Time: " + tempRequest.getEndTime()
						+ "\nThis request has been denied!\n\nThank you,\nAdmin");
		return new StringReturn("Denied meeting room request: " + requestId);
	}

	public StringReturn acceptMeetingRoomRequest(Long requestId) {
		MeetingRoomRequest tempRequest = meetingReqRepo.findById(requestId).orElse(null);
		MeetingRoom tempRoom = meetingRoomRepo.findById(tempRequest.getMeetingRoomId()).orElseThrow(null);
		tempRequest.setStatus(true);
		
		LocalTime timeStart = tempRequest.getStartTime().toLocalDateTime().toLocalTime();
		LocalTime timeEnd = tempRequest.getEndTime().toLocalDateTime().toLocalTime();
		LocalDate date = tempRequest.getStartTime().toLocalDateTime().toLocalDate();

		Set<LocalTime> time = tempRoom.getReserved().getOrDefault(date, new HashSet<>());
		long diff = ChronoUnit.MINUTES.between(timeStart, timeEnd);
		int nbSlots = Math.abs((int) (diff / 30));

		int inc = 0;
		for (int i = 0; i <= nbSlots; i++) {
			time.add(timeStart.plusMinutes(inc));
			inc += 30;
		}
		tempRoom.getReserved().put(date, time);
		meetingRoomRepo.save(tempRoom);
		meetingReqRepo.save(tempRequest);
		return new StringReturn(sender.sendingMail(tempRequest.getEmpId().getUserLogin().getUserName(),
				"Meeting Room Request Notification",
				"Request Id: " + tempRequest.getRequestId() + "\nDescription: " + tempRequest.getMeetingDesc()
						+ "\n Start Date: " + tempRequest.getStartTime() + "\nEnd Date: " + tempRequest.getEndTime()
						+ "\nThis request has been approved!\n\nThank you,\nAdmin"));

	}

	public Iterable<MeetingRoom> viewMeetings() {

		return meetingRoomRepo.findAll();
	}

	public Iterable<TrainingRoom> viewTrainings() {

		return trainingRoomRepo.findAll();
	}

	public Iterable<TrainingRoomRequest> viewTrainingRequest() {

		return trainReqRepo.findAll();
	}

	public Iterable<MeetingRoomRequest> viewMeetingRequest() {

		return meetingReqRepo.findAll();
	}

	public Iterable<LeaveRequest> viewLeaveRequest() {

		return leaveReqRepo.findAll();
	}

	public StringReturn acceptLeaveRequest(Long requestId) {
		LeaveRequest acceptedRequest = leaveReqRepo.findById(requestId).orElseThrow(null);
		acceptedRequest.setStatue(true);
		leaveReqRepo.save(acceptedRequest);

		return new StringReturn(sender.sendingMail(acceptedRequest.getEmpId().getUserLogin().getUserName(), "Leave Request Notification",
				"Request Id: " + acceptedRequest.getLeaveId() + "\n Start Date: " + acceptedRequest.getStartDate()
						+ "\nEnd Date: " + acceptedRequest.getEndDate()
						+ "\nThis request has been approved!\n\nThank you,\nAdmin"));

	}

	public StringReturn denyLeaveRequest(Long requestId) {
		LeaveRequest deniedRequest = leaveReqRepo.findById(requestId).orElseThrow(null);
		leaveReqRepo.deleteById(requestId);

		return new StringReturn(sender.sendingMail(deniedRequest.getEmpId().getUserLogin().getUserName(), "Leave Request Notification",
				"Request Id: " + deniedRequest.getLeaveId() + "\n Start Date: " + deniedRequest.getStartDate()
						+ "\nEnd Date: " + deniedRequest.getEndDate()
						+ "\nThis request has been approved!\n\nThank you,\nAdmin"));

	}

}
