package com.university.repository.mongo;

import com.university.entity.mongo.SpecialityMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SpecialityMongoRepository extends MongoRepository<SpecialityMongo, UUID> {
}
