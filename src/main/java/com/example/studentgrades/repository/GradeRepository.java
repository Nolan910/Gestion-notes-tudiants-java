package com.example.studentgrades.repository;

import com.example.studentgrades.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);

    List<Grade> findByCourseId(Long courseId);
}
