package com.albert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;
import com.albert.model.TrainingRoomRequest;

@Repository
public interface TrainingRoomRequestRepo extends CrudRepository<TrainingRoomRequest, Long> {
	public List<TrainingRoomRequest> findByEmpId(Optional<Employee> empId);
}
