package com.university.mapper;

import com.university.dto.CourseDTO;
import com.university.dto.SpecialityDTO;
import com.university.entity.Speciality;
import com.university.entity.mongo.SpecialityMongo;
import org.mapstruct.Mapper;

public class SpecialityMapper {

    public static SpecialityMongo dtoToMongo(SpecialityDTO specialityDTO) {
        SpecialityMongo specialityMongo = new SpecialityMongo();
        specialityMongo.setId(specialityDTO.getId());
        specialityMongo.setName(specialityDTO.getName());
        for (CourseDTO courseDTO : specialityDTO.getCourses()) {
            specialityMongo.getCourses().add(CourseMapper.dtoToMongo(courseDTO));
        }
        return specialityMongo;
    }

    public static Speciality dtoToPostgres(SpecialityDTO specialityDTO) {
        Speciality speciality = new Speciality();
        speciality.setId(specialityDTO.getId());
        return speciality;
    }
}
