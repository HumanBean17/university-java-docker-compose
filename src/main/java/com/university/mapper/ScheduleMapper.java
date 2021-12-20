package com.university.mapper;

import com.university.dto.GroupDTO;
import com.university.dto.ScheduleDTO;
import com.university.dto.VisitDTO;
import com.university.entity.Group;
import com.university.entity.Schedule;
import com.university.entity.neo4j.ScheduleNeo;

import java.util.stream.Collectors;

public class ScheduleMapper {

    public static Schedule dtoToPostgres(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setLecture(LectureMapper.dtoToPostgres(scheduleDTO.getLecture()));
        for (GroupDTO group : scheduleDTO.getGroups()) {
            schedule.getGroups().add(GroupMapper.dtoToPostgres(group));
        }
        return schedule;
    }

    public static ScheduleNeo dtoToNeo(ScheduleDTO scheduleDTO) {
        ScheduleNeo scheduleNeo = new ScheduleNeo();
        scheduleNeo.setId(scheduleDTO.getId());
        scheduleNeo.setDate(scheduleDTO.getDate());
        scheduleNeo.setLecture(LectureMapper.dtoToNeo(scheduleDTO.getLecture(), true));
        for (GroupDTO group : scheduleDTO.getGroups()) {
            scheduleNeo.getGroups().add(GroupMapper.dtoToNeo(group));
        }
        return scheduleNeo;
    }
}
