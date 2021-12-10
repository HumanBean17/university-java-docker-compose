package com.university.dto;

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
public class SubjectDTO implements Serializable {

    UUID id;
    String name;
    Set<LectureDTO> lectures = new HashSet<>();
    CourseDTO course;
}
