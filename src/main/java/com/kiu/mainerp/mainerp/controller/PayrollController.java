package com.kiu.mainerp.mainerp.controller;

import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
import com.kiu.mainerp.mainerp.service.PayrollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/main-erp/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    @Autowired
    PayrollManager payrollManager;

    @RequestMapping(value = "/update-all-salary-info", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList updateAllSalaryInfo() throws ParseException {

        return payrollManager.getSalaryInfo();

    }



}
