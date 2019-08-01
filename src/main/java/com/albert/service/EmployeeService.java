package com.albert.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.albert.dao.EmployeeRepo;
import com.albert.dao.LeaveRequestRepo;
import com.albert.dao.MeetingRoomRepo;
import com.albert.dao.MeetingRoomRequestRepo;
import com.albert.dao.TaskRepo;
import com.albert.dao.TrainingRoomRequestRepo;
import com.albert.dao.UserLoginRepo;
import com.albert.model.DTOLeaveRequest;
import com.albert.model.DTOMeetingRoomRequest;
import com.albert.model.DTOTrainingRoomRequest;
import com.albert.model.Employee;
import com.albert.model.LeaveRequest;
import com.albert.model.MeetingRoomRequest;
import com.albert.model.StringReturn;
import com.albert.model.Task;
import com.albert.model.TrainingRoomRequest;
import com.albert.model.UserLogin;
import com.albert.service.EmployeeMailSender;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Service
public class EmployeeService {

	@Value("${admin.email}")
	private String adminEmail;
	@Autowired
	EmployeeRepo empRepo;
	@Autowired
	UserLoginRepo userRepo;
	@Autowired
	TaskRepo taskRepo;
	@Autowired
	EmployeeMailSender sender;
	@Autowired
	TrainingRoomRequestRepo trainingReqRepo;
	@Autowired
	MeetingRoomRepo meetingRoomRepo;
	@Autowired
	MeetingRoomRequestRepo meetingReqRepo;
	@Autowired
	LeaveRequestRepo leaveRepo;

	// gets all tasks assigned to employee
	public ArrayList<Task> getTasks(Long empId) {
		Optional<Employee> tempEmp = empRepo.findById(empId);
		return taskRepo.findByassignedTo(tempEmp);

	}

	// sets task as complete and send confirmation
	public StringReturn setTaskComplete(Long taskId) {
		Task tempTask = taskRepo.findById(taskId).orElse(null);
		if (tempTask != null) {
			tempTask.setStatus(true);
			taskRepo.save(tempTask);
			return new StringReturn(sender.sendingMail(tempTask.getAssignedBy().getUserLogin().getUserName(),
					"Task Modification Notification",
					"Task Id: " + tempTask.getTaskId()
							+ " has been modified successfully please & revert if any changes.\n\n" + "Thank you,\n"
							+ tempTask.getAssignedTo().getFirstName()));

		}

		return new StringReturn("Task not found");
	}

	// request a training room
	public StringReturn requestTrainingRoom(DTOTrainingRoomRequest dto, Long empId) {
		TrainingRoomRequest request = new TrainingRoomRequest(dto);
		request.setEmpId(empRepo.findById(empId).orElseThrow(null));
		trainingReqRepo.save(request);
		return new StringReturn(sender.sendingMail(adminEmail, "Training Room Request",
				"Request Id: " + request.getRequestId()

						+ "\nRoomId: " + request.getTrainingRoomId() + "\nStart Date: " + request.getStartDate()
						+ "\nEnd Date: " + request.getEndDate() + "\nDescription: " + request.getRoomDesc()));
	}

	public StringReturn requestMeetingRoom(DTOMeetingRoomRequest dto, Long empId) {
		MeetingRoomRequest request = new MeetingRoomRequest(dto);
		request.setEmpId(empRepo.findById(empId).orElseThrow(null));
		meetingReqRepo.save(request);
		return new StringReturn(sender.sendingMail(adminEmail, "Meeting Room Request",
				"Request Id: " + request.getRequestId() + "\nRoomId: " + request.getMeetingRoomId() + "\nStart Time: "
						+ request.getStartTime() + "\nEnd Time: " + request.getEndTime() + "\nDescription: "
						+ request.getMeetingDesc()));

	}

	public StringReturn requestLeave(DTOLeaveRequest dto, Long empId) {
		LeaveRequest request = new LeaveRequest(dto);
		request.setEmpId(empRepo.findById(empId).orElseThrow(null));

		leaveRepo.save(request);
		return new StringReturn(sender.sendingMail(adminEmail, "Leave Request",
				"Request Id: " + request.getLeaveId() + "\nLeave Tpye: " + request.getLeaveType() + "\nStart Date: "
						+ request.getStartDate() + "\nEnd Date: " + request.getEndDate() + "\nReason: "
						+ request.getReason()));

	}

	public List<LeaveRequest> viewLeaveRequest(Long empId) {
		return leaveRepo.findByEmpId(empRepo.findById(empId));

	}

	public List<MeetingRoomRequest> viewMeetingRequest(Long empId) {
		return meetingReqRepo.findByEmpId(empRepo.findById(empId));

	}

	public List<TrainingRoomRequest> viewTrainingRequest(Long empId) {
		return trainingReqRepo.findByEmpId(empRepo.findById(empId));

	}

	public Employee findUsersID(String user) {
		UserLogin tempUser = userRepo.findByuserName(user);
		Employee tempEmp = empRepo.findByuserLogin(tempUser);
		return tempEmp;

	}

	public StringReturn findUsersRole(String user) {
		UserLogin tempUser = userRepo.findByuserName(user);
		return new StringReturn(tempUser.getRole());
	}


}
