package com.university.mapper;

import com.university.dto.LectureDTO;
import com.university.dto.SubjectDTO;
import com.university.entity.Subject;
import com.university.entity.mongo.SubjectMongo;

public class SubjectMapper {

    public static SubjectMongo dtoToMongo(SubjectDTO subjectDTO) {
        SubjectMongo subjectMongo = new SubjectMongo();
        subjectMongo.setId(subjectDTO.getId());
        subjectMongo.setName(subjectDTO.getName());
        for (LectureDTO lectureDTO : subjectDTO.getLectures()) {
            subjectMongo.getLectures().add(LectureMapper.dtoToMongo(lectureDTO));
        }
        return subjectMongo;
    }

    public static Subject dtoToPostgres(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setId(subjectDTO.getId());
        subject.setCourse(CourseMapper.dtoToPostgres(subjectDTO.getCourse()));
        return subject;
    }

    public static SubjectDTO mongoToDTO(SubjectMongo subjectMongo) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subjectMongo.getId());
        subjectDTO.setName(subjectMongo.getName());
        subjectDTO.setLectures(null);
        subjectDTO.setCourse(null);
        return subjectDTO;
    }
}
