package com.kiu.mainerp.mainerp.service;

import com.kiu.mainerp.mainerp.response.ResponseList;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.ParseException;

public interface AttendanceManager {

    ResponseList getMyAttendance(int id) throws ParseException;

    ResponseList getMyYesterdayAttendance(int id) throws ParseException;

    ResponseList getMyYesterdayAttendanceForEmp(int id, int user) throws ParseException;

    ResponseList createAutoAttendance() throws ParseException;

    Resource getFileAsResource(String fileCode) throws IOException;

}
