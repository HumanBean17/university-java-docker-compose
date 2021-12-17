package com.university.mapper;

import com.university.dto.LectureDTO;
import com.university.dto.ScheduleDTO;
import com.university.entity.Lecture;
import com.university.entity.elastic.LectureElastic;
import com.university.entity.mongo.LectureMongo;
import com.university.entity.neo4j.LectureNeo;

public class LectureMapper {

    public static LectureMongo dtoToMongo(LectureDTO lectureDTO) {
        return new LectureMongo(lectureDTO.getId(), lectureDTO.getName());
    }

    public static Lecture dtoToPostgres(LectureDTO lectureDTO) {
        return new Lecture(lectureDTO.getId(), lectureDTO.getSubject());
    }

    public static LectureElastic dtoToElastic(LectureDTO lectureDTO) {
        return new LectureElastic(lectureDTO.getId().toString(), lectureDTO.getText());
    }

    public static LectureNeo dtoToNeo(LectureDTO lectureDTO, boolean callFromScheduleMapper) {
        LectureNeo lecture = new LectureNeo();
        lecture.setId(lectureDTO.getId());
        lecture.setSpecial(lectureDTO.isSpecial());
        for (ScheduleDTO scheduleDTO : lectureDTO.getSchedules()) {
            if (callFromScheduleMapper) {
                break;
            }
            lecture.getSchedules().add(ScheduleMapper.dtoToNeo(scheduleDTO));
        }
        return lecture;
    }
}
