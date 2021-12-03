package com.university.mapper;

import com.university.dto.LectureDTO;
import com.university.entity.Lecture;
import com.university.entity.LectureElastic;

public class LectureMapper {

    public static LectureElastic dtoToElasticEntity(LectureDTO dto) {
        LectureElastic lectureElastic = new LectureElastic();
        lectureElastic.setText(dto.getText());
        lectureElastic.setId(dto.getId().toString());
        return lectureElastic;
    }

    public static Lecture dtoToPostgreEntity(LectureDTO dto) {
        Lecture lecture = new Lecture();
        lecture.setId(dto.getId());
        lecture.setName(dto.getName());
        lecture.setSubject(dto.getSubject());
        return lecture;
    }
}
