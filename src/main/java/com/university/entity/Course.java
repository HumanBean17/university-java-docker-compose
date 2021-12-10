package com.university.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
//    @Column(name = "hours")
//    long hours;

    @ToString.Exclude
    @OneToMany(targetEntity = Speciality.class, cascade = CascadeType.ALL)
    Set<Speciality> specialities = new HashSet<>();
}
