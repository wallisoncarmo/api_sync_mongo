package com.carmowallison.api_sync_mongo.repositoties;

import com.carmowallison.api_sync_mongo.domain.Produto;
import com.carmowallison.api_sync_mongo.domain.sync.Sync;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SyncRepository extends MongoRepository<Sync, String> {
    List<Sync> findByCurrentGreaterThanOrderByCurrentAsc(Date current);

}
