package com.university.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements Serializable {

    UUID id;
    Integer hours;
    String name;
    Set<SubjectDTO> subjects = new HashSet<>();
    @JsonIgnore
    Set<SpecialityDTO> specialities = new HashSet<>();
}
