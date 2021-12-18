package com.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "groups")
public class Group implements Serializable {

    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "group_code", nullable = false)
    String groupCode;

//    @ToString.Exclude
//    @JsonIgnore
//    @OneToMany(targetEntity = Schedule.class)
//    Set<Schedule> schedules = new HashSet<>();

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    Set<Student> students = new HashSet<>();

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "speciality_id")
    Speciality speciality;

}
