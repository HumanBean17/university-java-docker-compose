package com.university.dto;

import com.university.entity.Schedule;
import com.university.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitDTO implements Serializable {

    UUID id;
    boolean visited;

    ScheduleDTO schedule;
    StudentDTO student;
}
