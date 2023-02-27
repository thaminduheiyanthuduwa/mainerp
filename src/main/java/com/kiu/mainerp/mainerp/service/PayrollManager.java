package com.kiu.mainerp.mainerp.service;

import com.kiu.mainerp.mainerp.response.ResponseList;

import java.text.ParseException;

public interface PayrollManager {

    ResponseList getSalaryInfo() throws ParseException;

}
