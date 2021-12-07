package com.university.entity.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node(value = "visit")
public class VisitNeo implements Serializable {

    @Id
    UUID id;

    @Relationship(type = "STUDENT", direction = Relationship.Direction.OUTGOING)
    Set<StudentNeo> students = new HashSet<>();
}
