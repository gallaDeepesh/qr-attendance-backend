package com.qrattendance.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qrattendance.attendance.entity.Attendance;
import com.qrattendance.attendance.entity.Student;
import com.qrattendance.attendance.entity.Session;

import java.util.Optional;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByStudentAndSession(Student student, Session session);

    List<Attendance> findBySession(Session session);
    Optional<Attendance> findBySessionAndDeviceId(Session session, String deviceId);
}
