package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.EmployeeLeaves;

@Repository
public interface EmployeeLeavesRepo extends CrudRepository<EmployeeLeaves, Long> {

}
