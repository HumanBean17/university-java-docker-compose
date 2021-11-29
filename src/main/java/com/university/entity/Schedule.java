package com.university.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne(targetEntity = Lecture.class)
    @ToString.Exclude
    Lecture lecture;

    @OneToMany(targetEntity = Group.class)
    @ToString.Exclude
    Set<Group> groups = new HashSet<>();

}
