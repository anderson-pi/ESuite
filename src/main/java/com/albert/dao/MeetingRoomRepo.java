package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.MeetingRoom;

@Repository
public interface MeetingRoomRepo extends CrudRepository<MeetingRoom, Long>{

}
