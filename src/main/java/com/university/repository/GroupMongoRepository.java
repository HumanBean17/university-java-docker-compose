package com.university.repository;

import com.university.entity.GroupMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface GroupMongoRepository extends MongoRepository<GroupMongo, UUID> {
}
