package com.kiu.mainerp.mainerp.repository;

import com.kiu.mainerp.mainerp.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer > {

    List<AttendanceEntity> findAll();

    @Query(nativeQuery = true,
            value = "select * from hr_attendance where created_at > :date")
    List<AttendanceEntity> getAttendanceAfterDate(@Param("date") String date);

    List<AttendanceEntity> findByEmployeeIdOrderByIdDesc(@Param("employeeId") int id);

}
