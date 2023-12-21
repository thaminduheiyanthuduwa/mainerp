package com.kiu.mainerp.mainerp.service.impl;


import com.kiu.mainerp.mainerp.const_codes.VariableCodes;
import com.kiu.mainerp.mainerp.models.OutstandingDataModel;
import com.kiu.mainerp.mainerp.models.OutstandingResponseModel;
import com.kiu.mainerp.mainerp.repository.AttendanceRepository;
import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
            outstandingDataModel.setRegisteredDate((Long)items.get(VariableCodes.REGISTERED_DATE));
            outstandingDataModel.setInstallmentType(installmentType);
            outstandingDataModel.setStudentId((Integer) items.get(VariableCodes.STUDENT_ID));
            outstandingDataModel.setInstallmentCounter(installmentCount);
            outstandingDataModel.setPaymentType(paymentType(installmentCount,installmentType));
            outstandingList.add(outstandingDataModel);
            amount+=dueAmount;
        }
        outstandingResponseModel.setTotalDueAmount(amount);
        outstandingResponseModel.setDataList(outstandingList);
        responseList.setData(outstandingResponseModel);
        return responseList;
    }
    public String paymentType(Integer count,String type){
        return type+" "+count;
    }

}