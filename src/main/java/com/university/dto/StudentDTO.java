package com.university.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.entity.Group;
import com.university.entity.Speciality;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO implements Serializable {

    String id;
    String name;
    GroupDTO group;
    @JsonIgnore
    SpecialityDTO speciality;
    Integer visit;
}
