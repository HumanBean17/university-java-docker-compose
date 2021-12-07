package com.university.dto;

import com.university.entity.Subject;
import com.university.entity.neo4j.ScheduleNeo;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LectureDTO implements Serializable {

    UUID id;
    String name;
    String text;
    Subject subject;
    Set<ScheduleNeo> scheduleNeo = new HashSet<>();
}
