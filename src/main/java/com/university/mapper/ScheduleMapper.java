package com.university.mapper;

import com.university.dto.ScheduleDTO;
import com.university.dto.VisitDTO;
import com.university.entity.Schedule;
import com.university.entity.neo4j.ScheduleNeo;

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
        scheduleNeo.setId(scheduleDTO.getId());
        for (VisitDTO visitDTO : scheduleDTO.getVisits()) {
            scheduleNeo.getVisits().add(VisitMapper.dtoToNeo(visitDTO));
        }
        return scheduleNeo;
    }
}
