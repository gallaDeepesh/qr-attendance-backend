package com.qrattendance.attendance.controller;

import com.qrattendance.attendance.entity.Session;
import com.qrattendance.attendance.service.SessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
//@CrossOrigin(origins = "http://localhost:5173")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public Session createSession(
            @RequestParam String sessionName,
            @RequestParam int validMinutes) {

        return sessionService.createSession(sessionName, validMinutes);
    }
}
