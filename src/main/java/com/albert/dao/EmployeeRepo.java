package com.albert.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee,Long> {
	List<Employee> findAllBylastName(String lastName);
}
