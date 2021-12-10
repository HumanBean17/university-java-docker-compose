package com.university.dto;

import com.university.entity.mongo.CourseMongo;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LabTwoDTO implements Serializable {

    Integer audienceCapacity;
    CourseMongo course;

}
