package com.kiu.mainerp.mainerp.service;

import com.kiu.mainerp.mainerp.response.ResponseList;

import java.text.ParseException;

public interface AttendanceManager {

    ResponseList getMyAttendance(int id) throws ParseException;

    ResponseList getMyYesterdayAttendance() throws ParseException;

    ResponseList createAutoAttendance() throws ParseException;

}
