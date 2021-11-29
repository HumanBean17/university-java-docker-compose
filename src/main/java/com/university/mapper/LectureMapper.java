package com.university.mapper;

import com.university.dto.LectureDTO;
import com.university.entity.Lecture;
import com.university.entity.LectureText;

public class LectureMapper {

    public static LectureText dtoToElasticEntity(LectureDTO dto) {
        LectureText lectureText = new LectureText();
        lectureText.setText(dto.getText());
        lectureText.setId(dto.getId().toString());
        return lectureText;
    }

    public static Lecture dtoToPostgreEntity(LectureDTO dto) {
        Lecture lecture = new Lecture();
        lecture.setId(dto.getId());
        lecture.setName(dto.getName());
        lecture.setSubject(dto.getSubject());
        return lecture;
    }
}
