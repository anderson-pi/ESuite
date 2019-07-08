package com.albert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;
import com.albert.model.Task;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {
	List<Task> findByassignedTo(Optional<Employee> tempEmp);
}
