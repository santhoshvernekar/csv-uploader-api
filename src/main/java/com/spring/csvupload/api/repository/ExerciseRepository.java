package com.spring.csvupload.api.repository;

import com.spring.csvupload.api.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("select t from Exercise t where t.code = :code")
    Exercise getByCode(String code);
}
