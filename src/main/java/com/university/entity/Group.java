package com.university.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import liquibase.pro.packaged.J;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
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
    UUID groupCode;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    Speciality speciality;

}
