package com.university.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class Course implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;
    @Column(name = "hours")
    long hours;

    @OneToMany(targetEntity = Speciality.class)
    Set<Speciality> specialities = new HashSet<>();
}
