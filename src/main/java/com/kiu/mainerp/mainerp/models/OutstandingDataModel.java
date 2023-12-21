package com.kiu.mainerp.mainerp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OutstandingDataModel {
    @JsonProperty("due_amount")
    private Double dueAmount;
    @JsonProperty("batch_name")
    private String batchName;
    @JsonProperty("status")
    private String status;
    @JsonProperty("due_date")
    private Date dueDate;
    @JsonProperty("name_initials")
    private String nameInitials;
    @JsonProperty("registered_date")
    private Long registeredDate;
    @JsonProperty("installment_type")
    private String installmentType;
    @JsonProperty("student_id")
    private Integer studentId;
    @JsonProperty("installment_counter")
    private Integer installmentCounter;
    @JsonProperty("payment_type")
    private String paymentType;
}
