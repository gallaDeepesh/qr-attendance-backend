package com.qrattendance.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qrattendance.attendance.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRollNo(String rollNo);
}
