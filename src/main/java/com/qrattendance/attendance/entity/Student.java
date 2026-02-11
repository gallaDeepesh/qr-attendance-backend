
package com.qrattendance.attendance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false, unique = true)
    private String rollNo;

    @Column(nullable = false)
    private String name;

    // Constructors
    public Student() {
    }

    public Student(String rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }
// getters and setters
    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
