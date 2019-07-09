package com.albert.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.albert.model.TrainingRoomRequest;

@Repository
public interface TrainingRoomRequestRepo extends CrudRepository<TrainingRoomRequest, Long> {

}
