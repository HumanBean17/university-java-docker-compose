package com.university.entity.neo4j;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node(value = "student")
public class StudentNeo implements Serializable {

    @Id
    UUID id;

}
