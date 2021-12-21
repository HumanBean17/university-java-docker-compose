package com.university.repository;

import com.university.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {

    @Query(value = "SELECT * FROM visit_?1 v WHERE v.student_id = ?2", nativeQuery = true)
    List<Visit> findAllByStudent_Id(String partitionNum, String studentId);

    List<Visit> findAllByStudent_Id(String studentId);
}
