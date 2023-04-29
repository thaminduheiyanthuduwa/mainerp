package com.kiu.mainerp.mainerp.controller;

import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.AttendanceManager;
import com.kiu.mainerp.mainerp.service.PeopleManager;
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
@RequestMapping("/main-erp/people")
@CrossOrigin(origins = "*")
public class PeopleController {

    @Autowired
    PeopleManager peopleManager;

    @RequestMapping(value = "/update-people-list", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseList updatePeopleList() throws ParseException {

        return peopleManager.updatePeopleTable();

    }

}
