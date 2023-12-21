package com.kiu.mainerp.mainerp.service.impl;


import com.kiu.mainerp.mainerp.const_codes.VariableCodes;
import com.kiu.mainerp.mainerp.models.out_standing.OutstandingDataModel;
import com.kiu.mainerp.mainerp.models.out_standing.OutstandingResponseModel;
import com.kiu.mainerp.mainerp.models.out_standing_other_payments.OutStandingOtherPaymentDataModel;
import com.kiu.mainerp.mainerp.models.out_standing_other_payments.OutStandingOtherPaymentResponseModel;
import com.kiu.mainerp.mainerp.repository.AttendanceRepository;
import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public ResponseList getAllData(int offSet, int pageSize) throws ParseException {
        ResponseList responseList = new ResponseList();

        Page<Map<String, Object>> all = attendanceRepository.gettingAllInfo(PageRequest.of(offSet, pageSize));

        responseList.setCode(200);
        responseList.setData(all);
        return responseList;
    }

    @Override
    public ResponseList getIncomeData() throws ParseException {
        ResponseList responseList = new ResponseList();
        List<Map<String, Object>> allIncome = attendanceRepository.gettinIncomeInfo();
        responseList.setCode(200);
        responseList.setData(allIncome);
        return responseList;
    }

    @Override
    public ResponseList getAll() throws ParseException {
        ResponseList responseList = new ResponseList();
        List<Map<String, Object>> all = attendanceRepository.allInfo();
        responseList.setCode(200);
        responseList.setData(all);
        return responseList;
    }

    @Override
    public ResponseList getAllIncomeDataPaginated(int offSet, int pageSize) throws ParseException {
        ResponseList responseList = new ResponseList();
        Page<Map<String, Object>> incomePaginated = attendanceRepository.gettingIncomeInfoPageination(PageRequest.of(offSet, pageSize));
        responseList.setCode(200);
        responseList.setData(incomePaginated);
        return responseList;
    }

    @Override
    public ResponseList getDueReports(Integer dateRange) throws ParseException {
        ResponseList responseList = new ResponseList();
        List<Map<String, Object>> fetchDueReports = attendanceRepository.fetchDueReports(dateRange);
        responseList.setCode(200);
        responseList.setData(fetchDueReports);
        return responseList;
    }

    @Override
    public ResponseList getStudentsWithoutPaymentCards() throws ParseException {
        ResponseList responseList = new ResponseList();
        List<Map<String, Object>> getStudentsWithoutPaymentCards = attendanceRepository.getStudentsWithoutPaymentCards();
        responseList.setCode(200);
        responseList.setData(getStudentsWithoutPaymentCards);
        return responseList;
    }

    @Override
    public ResponseList getOutStandingReport(String startDate,String endDate) throws ParseException {
        ResponseList responseList=new ResponseList();
       Double amount=0.0;
       List<OutstandingDataModel> outstandingList=new ArrayList<>();
        OutstandingResponseModel outstandingResponseModel=new OutstandingResponseModel();
        List<Map<String,Object>> getOutStandingReport=attendanceRepository.outStandingReport(startDate, endDate);
        for (Map<String,Object> items:getOutStandingReport) {
            Integer installmentCount=(Integer) items.get(VariableCodes.INSTALLMENT_COUNTER);
            Double dueAmount= (Double)items.get( VariableCodes.DUE_AMOUNT);
            String installmentType=(String) items.get(VariableCodes.INSTALLMENT_TYPE);
            OutstandingDataModel outstandingDataModel=new OutstandingDataModel();
            outstandingDataModel.setBatchName((String) items.get(VariableCodes.BATCH_NAME));
            outstandingDataModel.setStatus((String) items.get(VariableCodes.STATUS));
            outstandingDataModel.setDueDate((Date) items.get(VariableCodes.DUE_DATE));
            outstandingDataModel.setNameInitials((String) items.get(VariableCodes.NAME_INITIAL));
            outstandingDataModel.setDueAmount(dueAmount);
            outstandingDataModel.setRegisteredDate((Date)items.get(VariableCodes.REGISTERED_DATE));
            outstandingDataModel.setInstallmentType(installmentType);
            outstandingDataModel.setStudentId((Integer) items.get(VariableCodes.STUDENT_ID));
            outstandingDataModel.setInstallmentCounter(installmentCount);
            outstandingDataModel.setPaymentType(paymentType(installmentCount,installmentType));
            outstandingList.add(outstandingDataModel);
            amount+=dueAmount;
        }
        outstandingResponseModel.setTotalDueAmount(amount);
        outstandingResponseModel.setDataList(outstandingList);
        responseList.setCode(200);
        responseList.setMsg("Successfully!");
        responseList.setData(outstandingResponseModel);
        return responseList;
    }
    public String paymentType(Integer count,String type){
        return type+" "+count;
    }

    @Override
    public ResponseList getOtherPaymentOutStandingReport(String startDate, String endDate) throws ParseException {
      ResponseList responseList=new ResponseList();
        OutStandingOtherPaymentResponseModel outStandingOtherPaymentResponseModel=new OutStandingOtherPaymentResponseModel();
      List<Map<String,Object>> filterStartDateAndEndDate=new ArrayList<>();
      List<OutStandingOtherPaymentDataModel> outStandingOtherPaymentDataModels=new ArrayList<>();
      Double dueAmount=0.0;
       List<Map<String,Object>>otherPaymentOutStandingReport=attendanceRepository.outStandingOtherPaymentReport();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateParsed = dateFormat.parse(startDate);
        Date endDateParsed = dateFormat.parse(endDate);
        for (Map<String,Object> items:otherPaymentOutStandingReport) {
            Date dueDate = (Date) items.get(VariableCodes.DUE_DATE);
            if (dueDate != null && !dueDate.before(startDateParsed) && !dueDate.after(endDateParsed)) {
                filterStartDateAndEndDate.add(items);
            }
        }
        for (Map<String,Object> items:filterStartDateAndEndDate) {
            OutStandingOtherPaymentDataModel outStandingOtherPaymentDataModel=new OutStandingOtherPaymentDataModel();
            Double amount=(Double) items.get(VariableCodes.DUE_AMOUNT);
            outStandingOtherPaymentDataModel.setId((Long) items.get(VariableCodes.ID));
            outStandingOtherPaymentDataModel.setStatus((String) items.get(VariableCodes.STATUS));
            outStandingOtherPaymentDataModel.setAmount(amount);
            outStandingOtherPaymentDataModel.setStudentId((Long) items.get(VariableCodes.STUDENT_ID));
            outStandingOtherPaymentDataModel.setDueDate((Date) items.get(VariableCodes.DUE_DATE));
            outStandingOtherPaymentDataModel.setName((String) items.get(VariableCodes.NAME));
            outStandingOtherPaymentDataModels.add(outStandingOtherPaymentDataModel);
            dueAmount+=amount;
        }

        outStandingOtherPaymentResponseModel.setTotalDueAmount(BigDecimal.valueOf(dueAmount).toPlainString());
        outStandingOtherPaymentResponseModel.setDataList(outStandingOtherPaymentDataModels);
        responseList.setCode(200);
        responseList.setMsg("Successfully!");
        responseList.setData(outStandingOtherPaymentResponseModel);
        return responseList;
    }

    @Override
    public ResponseList getStudentPaymentPlanCards(String startDate,String endDate) throws ParseException {
       ResponseList responseList=new ResponseList();
       Double courseFee=0.0;
       List<Map<String,Object>>getStudentPaymentPlanCards=attendanceRepository.getStudentPaymentPlanCards();
        List<Map<String,Object>> filterStartDateAndEndDate=new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateParsed = dateFormat.parse(startDate);
        Date endDateParsed = dateFormat.parse(endDate);
        for (Map<String,Object> items:getStudentPaymentPlanCards) {
            Date dueDate = (Date) items.get(VariableCodes.DUE_DATE);
            if (dueDate != null && !dueDate.before(startDateParsed) && !dueDate.after(endDateParsed)) {
                filterStartDateAndEndDate.add(items);
            }
        }

        for (Map<String,Object> items:filterStartDateAndEndDate) {
            String installmentType=(String) items.get(VariableCodes.INSTALLMENT_TYPE);
            if(installmentType.equals(VariableCodes.COURSE_FEE)){
                //courseFee+=totalpaid;
            }
        }
        responseList.setData(filterStartDateAndEndDate);
return responseList;

    }
}