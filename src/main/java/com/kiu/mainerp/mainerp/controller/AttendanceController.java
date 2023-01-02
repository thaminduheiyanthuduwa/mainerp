package com.kiu.mainerp.mainerp.controller;

import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/main-erp")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    AttendanceManager taskManager;

    @RequestMapping(value = "/get-my-attendance/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList getMyAttendance(@PathVariable Integer id) throws ParseException {

        return taskManager.getMyAttendance(id);

    }

    @RequestMapping(value = "/get-my-yesterday-attendance", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList getMyYesterdayAttendance() throws ParseException {

        return taskManager.getMyYesterdayAttendance();

    }

    @RequestMapping(value = "/create-auto-attendance", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList createAutoAttendance() throws ParseException {

        return taskManager.createAutoAttendance();

    }

}
