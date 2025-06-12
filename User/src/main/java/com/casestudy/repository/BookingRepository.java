package com.casestudy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.model.BookingModel;

@Repository
public interface BookingRepository extends MongoRepository<BookingModel, String> {

	List<BookingModel> findByUserId(String userid);

}
