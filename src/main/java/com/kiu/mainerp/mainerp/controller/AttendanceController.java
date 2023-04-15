package com.kiu.mainerp.mainerp.controller;

import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @RequestMapping(value = "/get-my-yesterday-attendance/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList getMyYesterdayAttendance(@PathVariable(value = "id") int id) throws ParseException {

        return taskManager.getMyYesterdayAttendance(id);

    }

    @RequestMapping(value = "/create-auto-attendance", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList createAutoAttendance() throws ParseException {

        return taskManager.createAutoAttendance();

    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {


        Resource resource = null;
        try {
            resource = taskManager.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

}
