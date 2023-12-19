package com.kiu.mainerp.mainerp.repository;

import com.kiu.mainerp.mainerp.entity.AttendanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    @Query(value = "SELECT distinct FSPS.id AS DT_RowId, FSPS.id, FSPS.installment_counter, FSPS.status,\n" +
            "FSPS.amount, FSPS.total_paid, (FSPS.amount - FSPS.total_paid) as due_amount, FSPS.due_date, FSPPC.student_id, FSPPC.batch_id,\n" +
            "S.name_initials, S.tel_mobile1, S.range_id, B.batch_name, BT.description AS student_type, installment_type\n" +
            "FROM `finance_student_payment_schedules` FSPS\n" +
            "JOIN finance_student_payment_plan_cards FSPPC ON FSPS.payment_plan_card_id = FSPPC.id AND FSPPC.status = 'APPROVED'\n" +
            "JOIN students S ON FSPPC.student_id = S.student_id\n" +
            "JOIN batch_student BS ON S.range_id = BS.student_id AND BS.status = 0 AND BS.batch_id = FSPPC.batch_id\n" +
            "JOIN batch_types as BT ON BS.batch_type = BT.id\n" +
            "JOIN batches B ON FSPPC.batch_id = B.batch_id\n" +
            "JOIN finance_student_fee_definitions FSFD ON FSPS.fee_definition_id = FSFD.id\n" +
            "WHERE FSPPC.status = 'APPROVED' and FSPS.status <> 'PAID' and FSPS.due_date <= '2023-06-16'\n" +
            "ORDER BY `FSPS`.`status`  DESC;\n", nativeQuery = true)
    Page<Map<String, Object>> gettingAllInfo(Pageable pageable);

    @Query(value = "SELECT distinct FSPS.id AS DT_RowId, FSPS.id, FSPS.installment_counter, FSPS.status,\n" +
            "FSPS.amount, FSPS.total_paid, (FSPS.amount - FSPS.total_paid) as due_amount, FSPS.due_date, FSPPC.student_id, FSPPC.batch_id,\n" +
            "S.name_initials, S.tel_mobile1, S.range_id, B.batch_name, BT.description AS student_type, installment_type\n" +
            "FROM `finance_student_payment_schedules` FSPS\n" +
            "JOIN finance_student_payment_plan_cards FSPPC ON FSPS.payment_plan_card_id = FSPPC.id AND FSPPC.status = 'APPROVED'\n" +
            "JOIN students S ON FSPPC.student_id = S.student_id\n" +
            "JOIN batch_student BS ON S.range_id = BS.student_id AND BS.status = 0 AND BS.batch_id = FSPPC.batch_id\n" +
            "JOIN batch_types as BT ON BS.batch_type = BT.id\n" +
            "JOIN batches B ON FSPPC.batch_id = B.batch_id\n" +
            "JOIN finance_student_fee_definitions FSFD ON FSPS.fee_definition_id = FSFD.id\n" +
            "WHERE FSPPC.status = 'APPROVED' and FSPS.status <> 'PAID' and FSPS.due_date <= '2023-06-16'\n" +
            "ORDER BY `FSPS`.`status`  DESC;\n", nativeQuery = true)
    List<Map<String,Object>> allInfo();

    @Query(value = "SELECT distinct si.full_name,  si.range_id as student_id, ba.batch_name, fs.due_date, fcfd.description,\n" +
            "fsp.plan_type, fs.amount,  fs.currency, fs.total_paid, fs.status, fs.installment_counter,\n" +
            "bt.description\n" +
            "FROM finance_student_payment_schedules fs\n" +
            "LEFT JOIN finance_student_fee_definitions fee ON fs.fee_definition_id = fee.id\n" +
            "LEFT JOIN finance_student_payment_plan_cards fsp ON fsp.id =  fs.payment_plan_card_id\n" +
            "LEFT JOIN students si ON si.student_id = fsp.student_id\n" +
            "LEFT JOIN finance_student_fee_definitions fsf ON fsf.fee_definition_id = fs.fee_definition_id\n" +
            "LEFT JOIN finance_batch_fee_types fbft ON fee.fee_definition_id = fbft.id\n" +
            "LEFT JOIN finance_common_fee_definition fcfd ON fcfd.id = fbft.common_fee_id\n" +
            "LEFT JOIN finance_batch_payment_plan_types fbp ON fbp.id = fsf.payment_plan_types_id\n" +
            "LEFT JOIN batches ba ON fsp.batch_id = ba.batch_id  \n" +
            "LEFT JOIN batch_student bss ON bss.student_id = si.student_old_id\n" +
            "LEFT JOIN batch_types bt ON bt.id = bss.batch_type\n" +
            "WHERE fs.due_date >= '2023-04-01' and fs.due_date < '2023-05-01' and bt.id <> 5\n" +
            "ORDER BY `fs`.`due_date`  DESC;\n",nativeQuery = true)
    List<Map<String,Object>> gettinIncomeInfo();

    @Query(value = "SELECT distinct si.full_name,  si.range_id as student_id, ba.batch_name, fs.due_date, fcfd.description,\n" +
            "fsp.plan_type, fs.amount,  fs.currency, fs.total_paid, fs.status, fs.installment_counter,\n" +
            "bt.description\n" +
            "FROM finance_student_payment_schedules fs\n" +
            "LEFT JOIN finance_student_fee_definitions fee ON fs.fee_definition_id = fee.id\n" +
            "LEFT JOIN finance_student_payment_plan_cards fsp ON fsp.id =  fs.payment_plan_card_id\n" +
            "LEFT JOIN students si ON si.student_id = fsp.student_id\n" +
            "LEFT JOIN finance_student_fee_definitions fsf ON fsf.fee_definition_id = fs.fee_definition_id\n" +
            "LEFT JOIN finance_batch_fee_types fbft ON fee.fee_definition_id = fbft.id\n" +
            "LEFT JOIN finance_common_fee_definition fcfd ON fcfd.id = fbft.common_fee_id\n" +
            "LEFT JOIN finance_batch_payment_plan_types fbp ON fbp.id = fsf.payment_plan_types_id\n" +
            "LEFT JOIN batches ba ON fsp.batch_id = ba.batch_id  \n" +
            "LEFT JOIN batch_student bss ON bss.student_id = si.student_old_id\n" +
            "LEFT JOIN batch_types bt ON bt.id = bss.batch_type\n" +
            "WHERE fs.due_date >= '2023-04-01' and fs.due_date < '2023-05-01' and bt.id <> 5\n" +
            "ORDER BY `fs`.`due_date`  DESC;\n",nativeQuery = true)
    Page<Map<String, Object>> gettingIncomeInfoPageination(Pageable pageable);
    @Query(value = "SELECT obj1.degree, obj1.batch_name, obj1.admission_number, obj1.full_name, obj1.registration_fee_paid, obj1.course_fee_paid,\n" +
            "obj1.initial_fee_paid, obj1.total_paid_amount, obj1.interest, (obj1.interest + obj1.total_paid_amount) as total_with_fine, obj2.latest_payment_date, obj2.latest_payment_status,  obj2.latest_paid_amount, obj1.ins_due_from, DATEDIFF(CURDATE(), ins_due_from) as no_of_days_outstanding, obj1.currency, \n" +
            "obj1.tel_mobile1 as mobile_number, obj1.kiu_mail\n" +
            "FROM (SELECT\n" +
            "            'TBA' as degree,\n" +
            "            ba.batch_name,\n" +
            "            s.range_id as admission_number,           \n" +
            "            s.full_name, \n" +
            "            SUM(CASE WHEN (fd.installment_type = 'Registration' AND fs.status <> 'PAID') THEN fs.amount END) AS registration_fee_paid,\n" +
            "            SUM(CASE WHEN (fd.installment_type = 'Course Fee' AND fs.status <> 'PAID') THEN fs.amount END) AS course_fee_paid,\n" +
            "            SUM(CASE WHEN (fd.installment_type = 'Initial Fee' AND fs.status <> 'PAID') THEN fs.amount END) AS initial_fee_paid,\n" +
            "            COALESCE(SUM(CASE WHEN (fd.installment_type = 'Registration' AND fs.status <> 'PAID') THEN fs.amount END), 0) +\n" +
            "            COALESCE(SUM(CASE WHEN (fd.installment_type = 'Course Fee' AND fs.status <> 'PAID') THEN fs.amount END), 0) +\n" +
            "            COALESCE(SUM(CASE WHEN (fd.installment_type = 'Initial Fee' AND fs.status <> 'PAID') THEN fs.amount END), 0) as total_paid_amount,\n" +
            "            fs.late_payment as interest, MIN(fs.due_date) as ins_due_from, fs.currency, s.tel_mobile1, s.kiu_mail\n" +
            "        FROM\n" +
            "            finance_student_payment_schedules fs\n" +
            "        LEFT JOIN finance_student_fee_definitions fd ON fs.fee_definition_id = fd.id\n" +
            "        LEFT JOIN finance_student_payment_plan_cards fc ON fs.payment_plan_card_id = fc.id\n" +
            "        LEFT JOIN batch_student bs ON bs.student_id = fc.student_id\n" +
            "        LEFT JOIN students s ON s.range_id = fc.student_id\n" +
            "        LEFT JOIN batches ba ON bs.batch_id = ba.batch_id\n" +
            "        WHERE\n" +
            "             s.range_id IS NOT NULL\n" +
            "        GROUP BY\n" +
            "            s.range_id, ba.batch_name, admission_number, s.full_name\n" +
            "        HAVING \n" +
            "        \t MIN(fs.due_date) >= DATE_SUB(CURDATE(), INTERVAL 45 DAY)\n" +
            "            AND MIN(fs.due_date) <= CURDATE()) obj1\n" +
            "            LEFT JOIN (\n" +
            "\n" +
            "                SELECT\n" +
            "    fc.student_id,\n" +
            "    latest_payment.updated_at AS latest_payment_date,\n" +
            "    fs.status AS latest_payment_status,\n" +
            "    fs.total_paid AS latest_paid_amount\n" +
            "FROM\n" +
            "    finance_student_payment_plan_cards fc\n" +
            "LEFT JOIN (\n" +
            "    SELECT\n" +
            "        fs.payment_plan_card_id,\n" +
            "        MAX(fs.updated_at) AS updated_at\n" +
            "    FROM\n" +
            "        finance_student_payment_schedules fs\n" +
            "    WHERE\n" +
            "        fs.status IN ('PAID', 'PARTIAL')\n" +
            "    GROUP BY\n" +
            "        fs.payment_plan_card_id \n" +
            "    HAVING \n" +
            "    \tMIN(fs.due_date) >= DATE_SUB(CURDATE(), INTERVAL 45 DAY)\n" +
            "            AND MIN(fs.due_date) <= CURDATE()\n" +
            ") AS latest_payment ON fc.id = latest_payment.payment_plan_card_id\n" +
            "LEFT JOIN finance_student_payment_schedules fs ON fc.id = fs.payment_plan_card_id AND latest_payment.updated_at = fs.updated_at\n" +
            "WHERE\n" +
            "    fc.student_id IS NOT NULL AND fs.due_date IS NOT NULL\n" +
            "ORDER BY `latest_payment_date` DESC\n" +
            "\n" +
            "                ) obj2 ON obj2.student_id = obj1.admission_number  \n" +
            "ORDER BY `no_of_days_outstanding` ASC",nativeQuery = true)
    List<Map<String,Object>> fetchDueReports();
    @Query(value = "SELECT s.full_name, s.range_id, c.course_name,ba.batch_name,  IF(s.reg_date IS NULL, NULL, YEAR(s.reg_date)) as reg_date " +
            "FROM students s\n" +
            "LEFT JOIN finance_student_payment_plan_cards fs ON s.student_id = fs.student_id\n" +
            "LEFT JOIN batch_student bs ON bs.student_id = s.range_id\n" +
            "LEFT JOIN courses c ON c.course_id = bs.student_reg_courses_id\n" +
            "LEFT JOIN batches ba ON bs.batch_id = ba.batch_id\n" +
            "WHERE s.status = 1 AND fs.id IS NULL;",nativeQuery = true)
    List<Map<String,Object>>getStudentsWithoutPaymentCards();
}
