package com.university.mapper;

import com.university.dto.LectureDTO;
import com.university.dto.ScheduleDTO;
import com.university.entity.Lecture;
import com.university.entity.elastic.LectureElastic;
import com.university.entity.mongo.LectureMongo;
import com.university.entity.neo4j.LectureNeo;

public class LectureMapper {

    public static LectureMongo dtoToMongo(LectureDTO lectureDTO) {
        LectureMongo lectureMongo = new LectureMongo(lectureDTO.getId(), lectureDTO.getName());
        return lectureMongo;
    }

    public static Lecture dtoToPostgres(LectureDTO lectureDTO) {
        Lecture lecture = new Lecture(lectureDTO.getId(), lectureDTO.getSubject());
        return lecture;
    }

    public static LectureElastic dtoToElastic(LectureDTO lectureDTO) {
        LectureElastic lecture = new LectureElastic(lectureDTO.getId().toString(), lectureDTO.getText());
        return lecture;
    }

    public static LectureNeo dtoToNeo(LectureDTO lectureDTO, boolean callFromScheduleMapper) {
        LectureNeo lecture = new LectureNeo();
        lecture.setId(lectureDTO.getId());
        for (ScheduleDTO scheduleDTO : lectureDTO.getSchedules()) {
            if (callFromScheduleMapper == true) {
                break;
            }
            lecture.getSchedules().add(ScheduleMapper.dtoToNeo(scheduleDTO));
        }
        return lecture;
    }
}
