package com.casestudy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.model.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String>{

	UserModel findByusername(String username);

}