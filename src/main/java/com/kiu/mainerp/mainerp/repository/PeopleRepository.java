package com.kiu.mainerp.mainerp.repository;

import com.kiu.mainerp.mainerp.entity.AttendanceEntity;
import com.kiu.mainerp.mainerp.entity.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity, Integer > {

    List<PeopleEntity> findAll();

}
