package com.albert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;
import com.albert.model.MeetingRoomRequest;

@Repository
public interface MeetingRoomRequestRepo extends CrudRepository<MeetingRoomRequest, Long> {
	public List<MeetingRoomRequest> findByEmpId(Optional<Employee> empId);

}
