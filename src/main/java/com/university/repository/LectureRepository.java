package com.university.repository;

import com.university.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LectureRepository extends JpaRepository<Lecture, UUID> {
}
