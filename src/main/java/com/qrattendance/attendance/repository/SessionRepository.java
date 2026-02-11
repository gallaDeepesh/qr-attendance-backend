package com.qrattendance.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qrattendance.attendance.entity.Session;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {



    Optional<Session> findByToken(String token);
}
