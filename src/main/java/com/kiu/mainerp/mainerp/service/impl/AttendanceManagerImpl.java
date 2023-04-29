package com.kiu.mainerp.mainerp.service.impl;

import com.kiu.mainerp.mainerp.entity.AttendanceEntity;
import com.kiu.mainerp.mainerp.repository.AttendanceRepository;
import com.kiu.mainerp.mainerp.response.AttendanceObject;
import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
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

import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class AttendanceManagerImpl implements AttendanceManager {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Override
    public ResponseList getMyAttendance(int id) throws ParseException {

        Date date = new Date (System.currentTimeMillis());

        SimpleDateFormat convertDateToDateOnly=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat convertDateToTimeOnly=new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat convertStringToDateTypeTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<AttendanceEntity> data = attendanceRepository.findByEmployeeIdOrderByIdDesc(id);

        List<AttendanceObject> attendanceEntities = new ArrayList<>();

        for (AttendanceEntity obj : data) {
            if (convertDateToDateOnly.parse(convertDateToDateOnly.format(date)).before(obj.getDateTime())){
                AttendanceObject attendanceObject = new AttendanceObject();
                attendanceObject.setId(obj.getId());
                attendanceObject.setEmployeeId(obj.getEmployeeId());
                attendanceObject.setDateTime(obj.getDateTime());
                attendanceObject.setDateTimeText(convertDateToTimeOnly.format(obj.getDateTime()));
                attendanceObject.setCreatedAt(obj.getCreatedAt());
                attendanceObject.setDateTimeFullText(convertStringToDateTypeTwo.format(obj.getDateTime()));
                attendanceObject.setCreatedAtFullText(convertStringToDateTypeTwo.format(obj.getCreatedAt()));
                attendanceEntities.add(attendanceObject);
            }
        }

        ResponseList responseList = new ResponseList();
        responseList.setCode(200);
        responseList.setMsg("Attendance data find by id");
        responseList.setData(attendanceEntities);
        return responseList;
    }

    @Override
    public ResponseList getMyYesterdayAttendance(int id) throws ParseException {

        SimpleDateFormat convertStringToDateTypeTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date (System.currentTimeMillis() - 24 * 60 * 60 * 1000 * id);

        SimpleDateFormat convertDateToDateOnly=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat convertDateToTimeOnly=new SimpleDateFormat("HH:mm:ss");

        List<AttendanceEntity> data = attendanceRepository.getAttendanceAfterDate(convertDateToDateOnly.format(date));

        List<AttendanceObject> attendanceEntities = new ArrayList<>();

        for (AttendanceEntity obj : data) {
            if (convertDateToDateOnly.format(date).equalsIgnoreCase(convertDateToDateOnly.format(obj.getDateTime()))){
                AttendanceObject attendanceObject = new AttendanceObject();
                attendanceObject.setId(obj.getId());
                attendanceObject.setEmployeeId(obj.getEmployeeId());
                attendanceObject.setDateTime(obj.getDateTime());
                attendanceObject.setDateTimeText(convertDateToTimeOnly.format(obj.getDateTime()));
                attendanceObject.setCreatedAt(obj.getCreatedAt());
                attendanceObject.setDateTimeFullText(convertStringToDateTypeTwo.format(obj.getDateTime()));
                attendanceObject.setCreatedAtFullText(convertStringToDateTypeTwo.format(obj.getCreatedAt()));
                attendanceEntities.add(attendanceObject);
            }
        }


        ResponseList responseList = new ResponseList();
        responseList.setCode(200);
        responseList.setMsg("Attendance data find by id");
        responseList.setData(attendanceEntities);
        return responseList;
    }


    @Override
    public ResponseList getMyYesterdayAttendanceForEmp(int id, int user) throws ParseException {

        SimpleDateFormat convertStringToDateTypeTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        LocalDate today = LocalDate.now();
        LocalDate fiftyDaysAgo = today.minus(id, ChronoUnit.DAYS);
        Date date = Date.from(fiftyDaysAgo.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//        Date date = new Date (System.currentTimeMillis() - 24 * 60 * 60 * 1000 * id);

        SimpleDateFormat convertDateToDateOnly=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat convertDateToTimeOnly=new SimpleDateFormat("HH:mm:ss");

        List<AttendanceEntity> data = attendanceRepository.getAttendanceAfterDateForEmp(convertDateToDateOnly.format(date), user);

        List<AttendanceObject> attendanceEntities = new ArrayList<>();

        for (AttendanceEntity obj : data) {
            if (convertDateToDateOnly.format(date).equalsIgnoreCase(convertDateToDateOnly.format(obj.getDateTime()))){
                AttendanceObject attendanceObject = new AttendanceObject();
                attendanceObject.setId(obj.getId());
                attendanceObject.setEmployeeId(obj.getEmployeeId());
                attendanceObject.setDateTime(obj.getDateTime());
                attendanceObject.setDateTimeText(convertDateToTimeOnly.format(obj.getDateTime()));
                attendanceObject.setCreatedAt(obj.getCreatedAt());
                attendanceObject.setDateTimeFullText(convertStringToDateTypeTwo.format(obj.getDateTime()));
                attendanceObject.setCreatedAtFullText(convertStringToDateTypeTwo.format(obj.getCreatedAt()));
                attendanceEntities.add(attendanceObject);
            }
        }


        ResponseList responseList = new ResponseList();
        responseList.setCode(200);
        responseList.setMsg("Attendance data find by id");
        responseList.setData(attendanceEntities);
        return responseList;
    }

    @Override
    public ResponseList createAutoAttendance() throws ParseException {
        return null;
    }

    private Path foundFile;
    @Override
    public Resource getFileAsResource(String fileCode) throws IOException {

        Path dirPath = Paths.get("/");

        Files.list(Paths.get("/install")).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }

}
