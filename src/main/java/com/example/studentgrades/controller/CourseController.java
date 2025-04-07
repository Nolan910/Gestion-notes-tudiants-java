package com.example.studentgrades.controller;

import com.example.studentgrades.model.Course;
import com.example.studentgrades.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody @Valid Course course) {
        Course savedCourse = courseService.addCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody @Valid Course course) {
        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        course.setId(id);
        Course updatedCourse = courseService.updateCourse(id, course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
