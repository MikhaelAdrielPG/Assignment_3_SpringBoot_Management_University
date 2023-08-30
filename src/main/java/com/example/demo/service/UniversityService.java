package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService {
    private static final List<Student> students = new ArrayList<>();

    // Fungsi untuk Register Mahasiswa Baru
    public boolean registerStudent(Student student) {
        isvalidStudent(student) ;

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
                if (isValidUpdateStudent(updatedStudent)) {
                    student.setName(updatedStudent.getName());
                }
                if (isValidUpdateStudent(updatedStudent)) {
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
                    if (isValidCourseUpdate(updatedCourse)) {
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
        isValidCourse(course);

        if (getStudent(studentId) != null) {
            for (Course existingCourse : getStudent(studentId).getCourses()) {
                if (existingCourse.getId() == course.getId()) {
                    return false; // ID kursus sudah ada
                }
            }

            getStudent(studentId).getCourses().add(course);
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

    // validasi nama student
    public boolean isValidName(String name) {
        // Define a regex pattern to allow only letters and spaces
        /*
        ^           : Menandakan awal dari string.
        [a-zA-Z\\s] : Cocok dengan karakter alfabet (baik huruf besar A-Z maupun kecil a-z) dan spasi (\\s).
        +           : Menandakan bahwa pola sebelumnya (yaitu [a-zA-Z\\s]) dapat muncul satu atau lebih kali.
        $           : Menandakan akhir dari string.
        * */
        String pattern = "^[a-zA-Z\\s]+$";

        return name.matches(pattern);
    }

    // Fungsi untuk validasi saat registrasi student
    public boolean isvalidStudent (Student student) {
        return student != null && student.getName() != null && !student.getName().trim().isEmpty() &&
                student.getMajor() != null && !student.getMajor().trim().isEmpty();
    }

    // Fungsi untuk validasi saat update student
    public boolean isValidUpdateStudent(Student updateStudent) {
        return updateStudent.getName() != null && !updateStudent.getName().trim().isEmpty();
    }

    // Fungsi untuk validasi saat update course
    public boolean isValidCourseUpdate(Course updateCourse) {
        return updateCourse.getName() != null && !updateCourse.getName().trim().isEmpty();
    }

    // Fungsi untuk Validasi course
    public boolean isValidCourse(Course course) {
        return course != null && course.getName() != null && !course.getName().trim().isEmpty();
    }
}