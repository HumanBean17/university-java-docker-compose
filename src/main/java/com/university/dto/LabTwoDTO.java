package com.university.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LabTwoDTO implements Serializable {

    Integer audienceCapacity;
    Integer numberOfStudents;
    CourseDTO course;

}
