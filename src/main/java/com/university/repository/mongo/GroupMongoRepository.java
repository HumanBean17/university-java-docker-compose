package com.university.repository.mongo;

import com.university.entity.mongo.GroupMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface GroupMongoRepository extends MongoRepository<GroupMongo, UUID> {
}
