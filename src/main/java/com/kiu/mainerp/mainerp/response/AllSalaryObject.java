package com.kiu.mainerp.mainerp.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AllSalaryObject {

    private String id;

    private String empId;

    private String name;

    private String category;

    private String type;

    private String amount;

    public AllSalaryObject(String id, String empId, String name, String category, String type, String amount) {
        this.id = id;
        this.empId = empId;
        this.name = name;
        this.category = category;
        this.type = type;
        this.amount = amount;
    }
}
