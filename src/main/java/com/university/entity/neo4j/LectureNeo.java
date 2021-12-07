package com.university.entity.neo4j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node(value = "lecture")
public class LectureNeo implements Serializable {

    @Id
    UUID id;

    @Relationship(type = "SCHEDULE", direction = Relationship.Direction.OUTGOING)
    Set<ScheduleNeo> schedules = new HashSet<>();
}
