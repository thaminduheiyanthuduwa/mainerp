package com.kiu.mainerp.mainerp.entity;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.*;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hr_attendance")
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "date_time")
    private Date dateTime;

    @Column(name = "created_at")
    private Date createdAt;
}
