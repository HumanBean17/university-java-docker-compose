package com.university.repository.neo4j;

import com.university.entity.Schedule;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.UUID;

public interface ScheduleNeoRepository extends Neo4jRepository<Schedule, UUID> {
}
