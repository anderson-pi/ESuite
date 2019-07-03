package com.albert.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.Department;

@Repository
public interface DeptRepo extends CrudRepository<Department,Long>{
	Department findBydeptName(String deptName);
}
