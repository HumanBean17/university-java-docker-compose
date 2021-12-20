package com.university.mapper;

import com.university.dto.VisitDTO;
import com.university.entity.Visit;
import com.university.entity.neo4j.VisitNeo;

import java.util.UUID;

public class VisitMapper {

    public static Visit dtoToPostgres(VisitDTO visitDTO) {
        Visit visit = new Visit();
        visit.setId(visitDTO.getId());
        visit.setVisited(visitDTO.isVisited());
        visit.setDate(visitDTO.getDate());
        visit.setSchedule(ScheduleMapper.dtoToPostgres(visitDTO.getSchedule()));
        visit.setStudent(StudentMapper.dtoToPostgres(visitDTO.getStudent()));
        return visit;
    }

    public static VisitNeo dtoToNeo(VisitDTO visitDTO) {
        VisitNeo visitNeo = new VisitNeo();
        visitNeo.setId(visitDTO.getId());
        visitNeo.setVisited(visitDTO.isVisited());
        visitNeo.setStudent(UUID.fromString(visitDTO.getStudent().getId()));
        return visitNeo;
    }
}
