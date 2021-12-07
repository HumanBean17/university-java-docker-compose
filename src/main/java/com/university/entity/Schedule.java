package com.university.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
// Hibernate
@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;
    @Column(name = "date")
    LocalDateTime date;

    // Hibernate
    @ManyToOne(targetEntity = Lecture.class)
    @ToString.Exclude
    Lecture lecture;

    @OneToMany(targetEntity = Group.class)
    @ToString.Exclude
    Set<Group> groups = new HashSet<>();

}
