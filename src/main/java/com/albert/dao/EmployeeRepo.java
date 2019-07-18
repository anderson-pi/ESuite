package com.albert.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Employee;
import com.albert.model.UserLogin;


@Repository
public interface EmployeeRepo extends CrudRepository<Employee,Long> {
	List<Employee> findAllBylastName(String lastName);
	List<Employee> findBydept(String dept);
	Employee findByuserLogin(UserLogin user);
}
