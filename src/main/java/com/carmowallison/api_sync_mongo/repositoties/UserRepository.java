package com.carmowallison.api_sync_mongo.repositoties;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carmowallison.api_sync_mongo.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmailContaining(String text);
	
}
