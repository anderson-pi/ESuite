package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.LeaveRequest;

@Repository
public interface LeaveRequestRepo extends CrudRepository<LeaveRequest, Long> {

}
