package com.kiu.mainerp.mainerp.service;

import com.kiu.mainerp.mainerp.response.ResponseList;

import java.text.ParseException;

public interface ReportService {
    ResponseList getAllData(int offSet, int pageSize) throws ParseException;
    ResponseList getOutstandingData(String date) throws ParseException;
    ResponseList getIncomeData(String startDate, String endDate) throws ParseException;
    ResponseList getAllIncomeDataPaginated(int offSet,int pageSize) throws  ParseException;
}