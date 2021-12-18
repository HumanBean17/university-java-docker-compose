package com.university.mapper;

import com.university.dto.CourseDTO;
import com.university.dto.SpecialityDTO;
import com.university.dto.SubjectDTO;
import com.university.entity.Course;
import com.university.entity.mongo.CourseMongo;

import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseMongo dtoToMongo(CourseDTO courseDTO) {
        CourseMongo courseMongo = new CourseMongo();
        courseMongo.setId(courseDTO.getId());
        courseMongo.setHour(courseDTO.getHours());
        courseMongo.setName(courseDTO.getName());
        for (SubjectDTO subjectDTO : courseDTO.getSubjects()) {
            courseMongo.getSubjects().add(SubjectMapper.dtoToMongo(subjectDTO));
        }
        return courseMongo;
    }

    public static Course dtoToPostgres(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setHours(courseDTO.getHours());
        course.setName(courseDTO.getName());
        for (SpecialityDTO speciality : courseDTO.getSpecialities()) {
            course.getSpecialities().add(SpecialityMapper.dtoToPostgres(speciality));
        }
        return course;
    }

    public static CourseDTO mongoToDTO(CourseMongo courseMongo) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(courseMongo.getId());
        courseDTO.setName(courseMongo.getName());
        courseDTO.setHours(courseMongo.getHour());
        courseDTO.setSubjects(courseMongo.getSubjects().stream().map(SubjectMapper::mongoToDTO).collect(Collectors.toSet()));
        return courseDTO;
    }

}
