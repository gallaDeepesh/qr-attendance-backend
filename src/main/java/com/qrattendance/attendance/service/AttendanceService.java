package com.qrattendance.attendance.service;

import com.qrattendance.attendance.entity.Attendance;
import com.qrattendance.attendance.entity.Session;
import com.qrattendance.attendance.entity.Student;
import com.qrattendance.attendance.repository.AttendanceRepository;
import com.qrattendance.attendance.repository.SessionRepository;
import com.qrattendance.attendance.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             StudentRepository studentRepository,
                             SessionService sessionService,
                             SessionRepository sessionRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.sessionService = sessionService;
        this. sessionRepository= sessionRepository;
    }

    public Attendance markAttendance(
            String rollNo,
            String name,
            String token,
            String deviceId
    ) {

        // 1️⃣ Validate inputs
        if (rollNo == null || rollNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Roll number is required");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (deviceId == null || deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid device");
        }

        token = token.trim();

        // 2️⃣ Validate session
        Session session = sessionRepository.findByToken(token)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid QR token"));

        if (session.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("QR Code has expired");
        }

        // 3️⃣ Prevent same device multiple attendance
        attendanceRepository.findBySessionAndDeviceId(session, deviceId)
                .ifPresent(a -> {
                    throw new IllegalStateException(
                            "This device has already marked attendance");
                });

        // 4️⃣ Find or create student
        Student student = studentRepository.findByRollNo(rollNo)
                .orElseGet(() -> studentRepository.save(
                        new Student(rollNo, name)));

        // 5️⃣ Prevent same student multiple attendance
        attendanceRepository.findByStudentAndSession(student, session)
                .ifPresent(a -> {
                    throw new IllegalStateException(
                            "Attendance already marked for this student");
                });

        // 6️⃣ Save attendance
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setSession(session);
        attendance.setDeviceId(deviceId);
        attendance.setTimestamp(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }


    public List<Attendance> getAttendanceBySession(Session session) {
        return attendanceRepository.findBySession(session);
    }

}
