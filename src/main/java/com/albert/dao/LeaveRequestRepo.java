package com.albert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;
import com.albert.model.LeaveRequest;

@Repository
public interface LeaveRequestRepo extends CrudRepository<LeaveRequest, Long> {
	public List<LeaveRequest> findByEmpId(Optional<Employee> empId);
}
