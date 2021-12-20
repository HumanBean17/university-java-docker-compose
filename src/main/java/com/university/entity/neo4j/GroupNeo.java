package com.university.entity.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node(value = "group")
public class GroupNeo implements Serializable {

    @Id
    UUID id;
    String groupCode;

//    @Relationship(direction = Relationship.Direction.OUTGOING)
    Set<UUID> students = new HashSet<>();
}
