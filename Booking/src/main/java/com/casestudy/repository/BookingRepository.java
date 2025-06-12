package com.casestudy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.casestudy.model.BookingModel;

public interface BookingRepository extends MongoRepository<BookingModel, String> {

	List<BookingModel> findByUserId(String userid);

	BookingModel findByPnrId(String pnrId);

}
