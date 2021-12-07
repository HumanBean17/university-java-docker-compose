package com.university.repository.neo4j;

import com.university.entity.neo4j.LectureNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LectureNeoRepository extends Neo4jRepository<LectureNeo, UUID> {
}
