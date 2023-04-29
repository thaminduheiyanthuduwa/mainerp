package com.kiu.mainerp.mainerp.service;

import com.kiu.mainerp.mainerp.response.ResponseList;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.text.ParseException;

public interface PeopleManager {

    ResponseList updatePeopleTable() throws ParseException;


}
