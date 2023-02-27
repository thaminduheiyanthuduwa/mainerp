package com.kiu.mainerp.mainerp.service.impl;

import com.kiu.mainerp.mainerp.entity.AttendanceEntity;
import com.kiu.mainerp.mainerp.repository.AttendanceRepository;
import com.kiu.mainerp.mainerp.response.AllSalaryObject;
import com.kiu.mainerp.mainerp.response.AttendanceObject;
import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
import com.kiu.mainerp.mainerp.service.PayrollManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayrollManagerImpl implements PayrollManager {

    @Autowired
    AttendanceRepository attendanceRepository;


    @Override
    public ResponseList getSalaryInfo() throws ParseException {

        List<Object[]> result = attendanceRepository.getAllSalaryInfo();

        List<AllSalaryObject> list = new ArrayList<>();

        result.forEach(objects -> {
            AllSalaryObject allSalaryObject = new AllSalaryObject(objects[0].toString(),
                    objects[1].toString(),objects[2] ==null ? "" : objects[2].toString(), objects[3].toString(),
                    objects[4] == null ? "": objects[4].toString(), objects[5] == null ? "0.00" :objects[5].toString());
            list.add(allSalaryObject);
        });

        ResponseList responseList = new ResponseList();
        responseList.setCode(200);
        responseList.setMsg("Attendance data find by id");
        responseList.setData(list);
        return responseList;
    }
}
