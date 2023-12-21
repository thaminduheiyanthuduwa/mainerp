package com.kiu.mainerp.mainerp.models.out_standing_other_payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class OutStandingOtherPaymentDataModel {
    private Long id;
    private String status;
    @JsonProperty("student_id")
    private Long studentId;
    private Double amount;
    @JsonProperty("due_date")
    private Date dueDate;
    private String name;

}
