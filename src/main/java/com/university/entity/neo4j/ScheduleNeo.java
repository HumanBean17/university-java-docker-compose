package com.university.entity.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node(value = "schedule")
public class ScheduleNeo implements Serializable {

    @Id
    UUID id;
    LocalDateTime date;

    @Relationship(type = "VISIT", direction = Relationship.Direction.OUTGOING)
    Set<VisitNeo> visits = new HashSet<>();

    @Relationship(type = "LECTURE", direction = Relationship.Direction.OUTGOING)
    LectureNeo lecture;

    Set<String> groups = new HashSet<>();
}
