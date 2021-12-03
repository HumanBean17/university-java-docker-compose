package com.university.repository;

import com.university.entity.StudentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface StudentMongoRepository extends MongoRepository<StudentMongo, UUID> {
}
