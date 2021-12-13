package com.university.mapper;

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
        schedule.getGroups().addAll(scheduleDTO.getGroups());
        return schedule;
    }

    public static ScheduleNeo dtoToNeo(ScheduleDTO scheduleDTO) {
        ScheduleNeo scheduleNeo = new ScheduleNeo();
        scheduleNeo.setDate(scheduleDTO.getDate());
        scheduleNeo.setLecture(LectureMapper.dtoToNeo(scheduleDTO.getLecture(), true));
        scheduleNeo.setId(scheduleDTO.getId());
        scheduleNeo.setGroups(scheduleDTO.getGroups().stream().map(Group::getGroupCode).collect(Collectors.toSet()));
        for (VisitDTO visitDTO : scheduleDTO.getVisits()) {
            scheduleNeo.getVisits().add(VisitMapper.dtoToNeo(visitDTO));
        }
        return scheduleNeo;
    }
}
