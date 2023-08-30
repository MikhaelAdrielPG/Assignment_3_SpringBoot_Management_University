package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService {
    private List<Student> students = new ArrayList<>();

    // Fungsi untuk Register Mahasiswa Baru
    public boolean registerStudent(Student student) {
        if (student == null || student.getName() == null || student.getName().trim().isEmpty() ||
                student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            return false;
        }

        // cek student dengan id yang sama , sudah ada
        for (Student existingStudent : students) {
            if (existingStudent.getId() == student.getId()) {
                return false; // ID mahasiswa sudah ada
            }
        }

        students.add(student);
        return true;
    }

    // Fungsi untuk menampilkan semua data mahasiswa
    public List<Student> getAllStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }


    // Fungsi untuk mendapatkan data mahasiswa berdasarkan ID
    public Student getStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id && student.isActive()) {
                return student;
            }
        }
        return null;
    }

    // Fungsi untuk mengupdate data mahasiswa
    public boolean updateStudent(int id, Student updatedStudent) {
        if (updatedStudent == null) {
            return false;
        }

        for (Student student : students) {
            if (student.getId() == id && student.isActive()) {
                if (updatedStudent.getName() != null && !updatedStudent.getName().trim().isEmpty()) {
                    student.setName(updatedStudent.getName());
                }
                if (updatedStudent.getMajor() != null && !updatedStudent.getMajor().trim().isEmpty()) {
                    student.setMajor(updatedStudent.getMajor());
                }
                if (updatedStudent.isActive() != student.isActive()) {
                    student.setActive(updatedStudent.isActive());
                }
                return true;
            }
        }
        return false;
    }

    // Fungsi untuk menonaktifkan status mahasiswa berdasarkan ID
    public boolean deactivateStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id && student.isActive()) {
                student.setActive(false);
                return true;
            }
        }
        return false;
    }

    // Fungsi untuk mengupdate data kursus berdasarkan ID
    public Course updateCourse(int id, Course updatedCourse) {
        if (updatedCourse == null) {
            return null;
        }

        for (Student student : students) {
            for (Course course : student.getCourses()) {
                if (course.getId() == id) {
                    if (updatedCourse.getName() != null && !updatedCourse.getName().trim().isEmpty()) {
                        course.setName(updatedCourse.getName().trim());
                    }
                    return course;
                }
            }
        }
        return null;
    }

    // Fungsi untuk menonaktifkan kursus berdasarkan ID kursus
    public boolean deactivateCourse(int courseId) {
        for (Student student : students) {
            for (Course course : student.getCourses()) {
                if (course.getId() == courseId && course.isActive()) {
                    course.setActive(false);
                    return true;
                }
            }
        }
        return false;
    }

    // Fungsi untuk mendaftarkan kursus ke mahasiswa berdasarkan ID mahasiswa
    public boolean enrollCourse(int studentId, Course course) {
        if (course == null || course.getName() == null || course.getName().trim().isEmpty()) {
            return false;
        }

        Student student = getStudent(studentId);
        if (student != null) {
            for (Course existingCourse : student.getCourses()) {
                if (existingCourse.getId() == course.getId()) {
                    return false; // ID kursus sudah ada
                }
            }

            student.getCourses().add(course);
            return true;
        } else {
            return false;
        }
    }

    // Fungsi untuk menambahkan nilai (grade) pada kursus
    public boolean addGrade(int studentId, int courseId, Grade grade) {
        if (grade == null) {
            return false;
        }

        if (getStudent(studentId) != null) {
            for (Course course : getStudent(studentId).getCourses()) {
                if (course.getId() == courseId && course.isActive()) {
                    course.getGrades().add(grade);
                    return true;
                }
            }
        }
        return false;
    }
}