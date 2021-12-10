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
@AllArgsConstructor
@NoArgsConstructor
@Node(value = "visit")
public class VisitNeo implements Serializable {

    @Id
    UUID id;
    boolean visited;

    //@Relationship(type = "STUDENT", direction = Relationship.Direction.OUTGOING)
    UUID student;
}
