package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.TrainingRoom;

@Repository
public interface TrainingRoomRepo extends CrudRepository<TrainingRoom, Long> {

}
