package com.university.repository.neo4j;

import com.university.entity.neo4j.VisitNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitNeoRepository extends Neo4jRepository<VisitNeo, UUID> {
}
