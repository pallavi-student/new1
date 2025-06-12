package com.casestudy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.model.TrainModel;

@Repository
public interface TrainRepository extends MongoRepository<TrainModel, String> {

	List<TrainModel> findByTrainFromAndTrainTo(String trainFrom, String trainTo);

	List<TrainModel> findByTrainName(String trainName);

	TrainModel findByTrainNo(String trainNo);

	List<TrainModel> findByTrainFrom(String trainFrom);

	List<TrainModel> findByTrainTo(String trainTo);

	
}
