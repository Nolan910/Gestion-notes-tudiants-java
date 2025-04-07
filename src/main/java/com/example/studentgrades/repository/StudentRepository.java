package com.example.studentgrades.repository;

import com.example.studentgrades.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
