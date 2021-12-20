package com.university.mapper;

import com.university.dto.GroupDTO;
import com.university.dto.StudentDTO;
import com.university.entity.Group;
import com.university.entity.neo4j.GroupNeo;

import java.util.UUID;

public class GroupMapper {

    public static Group dtoToPostgres(GroupDTO groupDTO) {
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setSpeciality(SpecialityMapper.dtoToPostgres(groupDTO.getSpeciality()));
        for (StudentDTO studentDTO : groupDTO.getStudents()) {
            group.getStudents().add(StudentMapper.dtoToPostgres(studentDTO));
        }
        return group;
    }

    public static GroupNeo dtoToNeo(GroupDTO groupDTO) {
        GroupNeo groupNeo = new GroupNeo();
        groupNeo.setId(groupDTO.getId());
        groupNeo.setGroupCode(groupDTO.getGroupCode());
        for (StudentDTO studentDTO : groupDTO.getStudents()) {
            groupNeo.getStudents().add(UUID.fromString(studentDTO.getId()));
        }
        return groupNeo;
    }
}
