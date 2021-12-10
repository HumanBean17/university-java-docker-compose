package com.university.dto;

import com.university.entity.Group;
import com.university.entity.Lecture;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO implements Serializable {

    UUID id;
    LocalDateTime date;

    LectureDTO lecture;
    Set<VisitDTO> visits = new HashSet<>();
    Set<Group> groups = new HashSet<>();
}
