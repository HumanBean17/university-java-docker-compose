package com.university.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;
    @Column(name = "date")
    LocalDateTime date;

    @OneToMany(targetEntity = Lecture.class)
    Set<Lecture> lectures = new HashSet<>();
    @OneToMany(targetEntity = Group.class)
    Set<Group> groups = new HashSet<>();

}
