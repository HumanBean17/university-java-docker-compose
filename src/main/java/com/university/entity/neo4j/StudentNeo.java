package com.university.entity.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;
import java.util.UUID;

@Node
public class StudentNeo implements Serializable {

    @Id
    UUID id;
}
