package com.university.repository.neo4j;

import com.university.entity.neo4j.ScheduleNeo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleNeoRepository extends Neo4jRepository<ScheduleNeo, UUID> {

    List<ScheduleNeo> findScheduleNeoByGroupsIn(Iterable<String> groups);

    List<ScheduleNeo> findAllByGroups(Iterable<String> groupId);
}
