package com.kiu.mainerp.mainerp.service.impl;


import com.kiu.mainerp.mainerp.repository.AttendanceRepository;
import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public ResponseList getAllData(int offSet, int pageSize) throws ParseException {
        ResponseList responseList = new ResponseList();

        Page<Map<String, Object>> all = attendanceRepository.gettingAllInfo(PageRequest.of(offSet, pageSize));

        responseList.setCode(200);
        responseList.setData(all);
        return responseList;
    }

    @Override
    public ResponseList getIncomeData(String startDate, String endDate) throws ParseException {
        ResponseList responseList = new ResponseList();
        List<Map<String, Object>> allIncome = attendanceRepository.gettingIncomeInfo(startDate, endDate);
        responseList.setCode(200);
        responseList.setData(allIncome);
        return responseList;
    }

    @Override
    public ResponseList getOutstandingData(String date) throws ParseException {
        ResponseList responseList = new ResponseList();
        List<Map<String, Object>> all = attendanceRepository.getAllOutstandingData(date);
        responseList.setCode(200);
        responseList.setData(all);
        return responseList;
    }

    @Override
    public ResponseList getAllIncomeDataPaginated(int offSet, int pageSize) throws ParseException {
        ResponseList responseList = new ResponseList();
        Page<Map<String, Object>> incomePaginated = attendanceRepository.gettingIncomeInfoPagination(PageRequest.of(offSet, pageSize));
        responseList.setCode(200);
        responseList.setData(incomePaginated);
        return responseList;
    }
}