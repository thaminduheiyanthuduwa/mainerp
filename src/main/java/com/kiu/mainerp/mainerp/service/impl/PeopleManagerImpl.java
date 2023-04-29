package com.kiu.mainerp.mainerp.service.impl;

import com.kiu.mainerp.mainerp.entity.AttendanceEntity;
import com.kiu.mainerp.mainerp.entity.PeopleEntity;
import com.kiu.mainerp.mainerp.repository.AttendanceRepository;
import com.kiu.mainerp.mainerp.repository.PeopleRepository;
import com.kiu.mainerp.mainerp.response.AttendanceObject;
import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
import com.kiu.mainerp.mainerp.service.PeopleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeopleManagerImpl implements PeopleManager {

    @Autowired
    PeopleRepository peopleRepository;


    @Override
    public ResponseList updatePeopleTable() throws ParseException {

        List<PeopleEntity> list = peopleRepository.findAll();

        ResponseList responseList = new ResponseList();
        responseList.setCode(200);
        responseList.setMsg("Attendance data find by id");
        responseList.setData(list);
        return responseList;
    }
}
