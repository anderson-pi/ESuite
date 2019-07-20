package com.albert.dao;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;
import com.albert.model.Task;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {
	ArrayList<Task> findByassignedTo(Optional<Employee> tempEmp);
}
