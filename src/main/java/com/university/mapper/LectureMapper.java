package com.university.mapper;

import com.university.dto.LectureDTO;
import com.university.entity.Lecture;
import com.university.entity.mongo.LectureMongo;
import org.mapstruct.Mapper;

@Mapper
public interface LectureMapper1 {

    LectureMongo dtoToMongo(LectureDTO lectureDTO);
    Lecture dtoToPostgres(LectureDTO lectureDTO);
}
