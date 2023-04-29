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

    @Query(nativeQuery = true,
            value = "select * from hr_attendance where created_at > :date and employee_id = :emp")
    List<AttendanceEntity> getAttendanceAfterDateForEmp(@Param("date") String date, @Param("emp") Integer emp);



    List<AttendanceEntity> findByEmployeeIdOrderByIdDesc(@Param("employeeId") int id);

    @Query(value = "select * from (select UUID() as unique_id, id, name_in_full, category, type, IFNULL(amount, 0) from(select * from (select pe.id, 2 as type_id,'Allowances' as category, pe.name_in_full, hca.name as type,  hra.amount   from people pe\n" +
            "left join hr_emp_param_configuration hec on pe.id = hec.employee_id\n" +
            "left join hr_emp_allowances hra on hra.employee_id = hec.id\n" +
            "left join hr_cmn_allowances hca on hca.id = hra.allowance_id order by pe.id) table1\n" +
            "UNION\n" +
            "select * from (select pe.id,1 as type_id,'Basic Salary' as category, pe.name_in_full, 'basic_salary' as type,  hec.basic_salary as amount from people pe\n" +
            "left join hr_emp_param_configuration hec on pe.id = hec.employee_id order by pe.id) table2\n" +
            "UNION\n" +
            "select * from (select pe.id, 3 as type_id, 'Deductions' as category, pe.name_in_full, hca.name as type,  hra.amount   from people pe\n" +
            "left join hr_emp_param_configuration hec on pe.id = hec.employee_id\n" +
            "left join hr_emp_deductions hra on hra.employee_id = hec.id\n" +
            "left join hr_deductions hca on hca.id = hra.deduction_id order by pe.id) table3\n" +
            "order by id, type_id) final_table\n" +
            "order by id) tab  ",
            nativeQuery = true)
    List<Object[]> getAllSalaryInfo();

}
