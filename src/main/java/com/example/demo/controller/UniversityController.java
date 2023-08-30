package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    // mendaftarkan mahasiswa baru
    @PostMapping("/student")
    public ResponseEntity registerStudent(@RequestBody Student student) {
        if (!universityService.isvalidStudent(student)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid student data.", null));
        }

        if (!universityService.isValidName(student.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid name format.", null));
        }

        if (universityService.registerStudent(student)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Student registered successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to register student.", null));
        }
    }

    // mengupdate data mahasiswa
    @PutMapping("/student/{id}")
    public ResponseEntity updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid student data.", null));
        }

        if (universityService.updateStudent(id, updatedStudent)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Student updated successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Student not found.", null));
        }
    }

    //  menonaktifkan status mahasiswa
    @DeleteMapping("/student/{id}")
    public ResponseEntity deactivateStudent(@PathVariable int id) {
        if (universityService.deactivateStudent(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Student deactived successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to deactivate student.", null));
        }
    }

    // mendaftarkan kursus ke mahasiswa
    @PostMapping("/student/{studentId}/enroll")
    public ResponseEntity enrollCourse(
            @PathVariable int studentId,
            @RequestBody Course course) {
        if (!universityService.isValidCourse(course)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid course data.", null));
        }

        if (universityService.enrollCourse(studentId, course)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Course enrolled successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to enroll course.", null));
        }
    }

    // menambahkan nilai (grade) pada kursus
    @PostMapping("/student/{studentId}/course/{courseId}/grade")
    public ResponseEntity addGrade(
            @PathVariable int studentId,
            @PathVariable int courseId,
            @RequestBody Grade grade) {
        if (grade == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid grade data.", null));
        }

        if (universityService.addGrade(studentId, courseId, grade)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Grade added successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to add grade.", null));
        }
    }

    // mendapatkan semua informasi mahasiswa
    @GetMapping("/students")
    public ResponseEntity getAllStudents() {
        List<Student> allStudents = universityService.getAllStudents();

        if (!allStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("List of all students retrieved successfully.", allStudents));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("No students found.", null));
        }
    }


    // mendapatkan informasi mahasiswa berdasarkan ID
    @GetMapping("/student/{id}")
    public ResponseEntity getStudentInfo(@PathVariable int id) {
        if (universityService.getStudent(id) != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Student information retrieved successfully.", universityService.getStudent(id)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Student not found.", null));
        }
    }

    // mendapatkan informasi kursus berdasarkan ID mahasiswa dan ID kursus
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity getCourseInfo(
            @PathVariable int studentId,
            @PathVariable int courseId) {
        if (universityService.getStudent(studentId) != null) {
            for (Course course :  universityService.getStudent(studentId).getCourses()) {
                if (course.getId() == courseId) {
                    if (course.isActive()) {
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(new ApiResponse("Course information retrieved successfully.", course));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse("Course is not active.", null));
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Course not found.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Student not found.", null));
        }
    }

    // mengupdate data kursus
    @PutMapping("/course/{id}")
    public ResponseEntity updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        if (updatedCourse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Invalid course data.", null));
        }

        if (universityService.updateCourse(id, updatedCourse) != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Course updated successfully.", universityService.updateCourse(id, updatedCourse)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Course not found.", null));
        }
    }

    // menonaktifkan kursus
    @DeleteMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity deactivateCourse(
            @PathVariable int studentId,
            @PathVariable int courseId) {
        if (universityService.deactivateCourse(courseId)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Course deactivated successfully.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Course not found.", null));
        }
    }
}