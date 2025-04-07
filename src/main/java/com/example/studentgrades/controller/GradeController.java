package com.example.studentgrades.controller;

import com.example.studentgrades.model.Grade;
import com.example.studentgrades.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping
    public ResponseEntity<Grade> addGrade(@RequestBody @Valid Grade grade) {
        Grade savedGrade = gradeService.addGrade(grade);
        return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody @Valid Grade grade) {
        Optional<Grade> existingGrade = gradeService.getGradeById(id);
        if (existingGrade.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        grade.setId(id);
        Grade updatedGrade = gradeService.updateGrade(id, grade);
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        Optional<Grade> existingGrade = gradeService.getGradeById(id);
        if (existingGrade.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/student/{studentId}")
    public List<Grade> getGradesByStudent(@PathVariable Long studentId) {
        return gradeService.getGradesByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Grade> getGradesByCourse(@PathVariable Long courseId) {
        return gradeService.getGradesByCourseId(courseId);
    }
}
