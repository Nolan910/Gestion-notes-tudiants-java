package com.example.studentgrades.repository;

import com.example.studentgrades.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
