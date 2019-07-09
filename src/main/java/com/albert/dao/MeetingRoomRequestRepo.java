package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.MeetingRoomRequest;

@Repository
public interface MeetingRoomRequestRepo extends CrudRepository<MeetingRoomRequest, Long> {

}
