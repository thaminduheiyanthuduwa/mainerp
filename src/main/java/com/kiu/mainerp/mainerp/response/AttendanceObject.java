package com.kiu.mainerp.mainerp.response;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class AttendanceObject {
    private int id;
    private int employeeId;

    private Date dateTime;

    private String dateTimeText;

    private String dateTimeFullText;

    private Date createdAt;

    private String createdAtFullText;

}
