package com.university.repository;

import com.university.entity.Lecture;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface LectureRepository extends ElasticsearchRepository<Lecture, UUID> {
}
