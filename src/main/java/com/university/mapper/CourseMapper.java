package com.university.mapper;

import com.university.dto.CourseDTO;
import com.university.dto.SpecialityDTO;
import com.university.dto.SubjectDTO;
import com.university.entity.Course;
import com.university.entity.mongo.CourseMongo;

public class CourseMapper {

    public static CourseMongo dtoToMongo(CourseDTO courseDTO) {
        CourseMongo courseMongo = new CourseMongo();
        courseMongo.setId(courseDTO.getId());
        courseMongo.setHour(courseDTO.getHours());
        courseMongo.setName(courseMongo.getName());
        for (SubjectDTO subjectDTO : courseDTO.getSubjects()) {
            courseMongo.getSubjects().add(SubjectMapper.dtoToMongo(subjectDTO));
        }
        return courseMongo;
    }

    public static Course dtoToPostgres(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        for (SpecialityDTO speciality : courseDTO.getSpecialities()) {
            course.getSpecialities().add(SpecialityMapper.dtoToPostgres(speciality));
        }
        return course;
    }

}
