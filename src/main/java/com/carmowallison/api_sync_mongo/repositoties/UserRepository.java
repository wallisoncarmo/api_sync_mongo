package com.carmowallison.api_sync_mongo.repositoties;

import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carmowallison.api_sync_mongo.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmailContaining(String text);

    List<User> findBySyncIn(List<Sync> list);
}
