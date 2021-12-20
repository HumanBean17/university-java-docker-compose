package com.university.repository.neo4j;

import com.university.entity.neo4j.StudentNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentNeoRepository extends Neo4jRepository<StudentNeo, UUID> {
}
