package com.qrattendance.attendance.controller;
import com.qrattendance.attendance.entity.Session;
import com.qrattendance.attendance.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.qrattendance.attendance.entity.Attendance;
import com.qrattendance.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final SessionRepository sessionRepository;

    public AttendanceController(AttendanceService attendanceService,SessionRepository sessionRepository) {
        this.attendanceService = attendanceService;
        this.sessionRepository=sessionRepository;
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(
            @RequestParam String rollNo,
            @RequestParam String name,
            @RequestParam String token,
            @RequestParam String deviceId) {

        try {
            Attendance attendance =
                    attendanceService.markAttendance(rollNo, name, token, deviceId);

            return ResponseEntity.ok(attendance); // 200 OK

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 409
        }
    }

    @GetMapping("/session/{sessionId}")
    public List<Attendance> getAttendanceForSession(@PathVariable Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        return attendanceService.getAttendanceBySession(session);
    }

}

