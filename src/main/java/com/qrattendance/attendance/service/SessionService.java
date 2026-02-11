package com.qrattendance.attendance.service;

import com.qrattendance.attendance.entity.Session;
import com.qrattendance.attendance.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    // Constructor Injection (Best practice)
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Create a new session and generate QR token
    public Session createSession(String sessionName, int validMinutes) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusMinutes(validMinutes);

        Session session = new Session();
        session.setSessionName(sessionName);
        session.setCreatedTime(now);
        session.setExpiryTime(expiry);
        session.setToken(UUID.randomUUID().toString());

        return sessionRepository.save(session);
    }

    // Validate QR token
    public Session validateSessionToken(String token) {

        Session session = sessionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid QR token"));

        if (session.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("QR Code has expired");
        }

        return session;
    }
}
