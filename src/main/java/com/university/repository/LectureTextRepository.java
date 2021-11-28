package com.university.repository;

import com.university.entity.LectureText;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends ElasticsearchRepository<LectureText, String> {


}
