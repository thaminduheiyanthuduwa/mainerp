package com.kiu.mainerp.mainerp.controller;

import com.kiu.mainerp.mainerp.response.ResponseList;
import com.kiu.mainerp.mainerp.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("/main-erp/report")
public class ReportController {
    @Autowired
    private ReportServiceImpl reportService;
    @GetMapping("/get-all/{offSet}/{pageSize}")
    public ResponseList getAllPageination(@PathVariable int offSet, @PathVariable int pageSize) throws ParseException {
        return  reportService.getAllData(offSet,pageSize);
    }
    @GetMapping("/all")
    public ResponseList getAll() throws  ParseException{
        return  reportService.getAll();
    }
    @GetMapping("/get-income")
    public ResponseList getAllIncomeData() throws  ParseException{
        return  reportService.getIncomeData();
    }
    @GetMapping("/get-income/{offSet}/{pageSize}")
    public ResponseList getIncomePaginated(@PathVariable int offSet,@PathVariable int pageSize) throws  ParseException{
        return reportService.getAllIncomeDataPaginated( offSet,pageSize);
    }
    @GetMapping("/get-due-reports/{dateRange}")
    public ResponseList getDueReports(@PathVariable Integer dateRange) throws  ParseException{
        return reportService.getDueReports(dateRange);
    }
    @GetMapping("/get-due-without-payment-cards")
    public ResponseList getStudentsWithoutPaymentCards() throws  ParseException{
        return reportService.getStudentsWithoutPaymentCards();
    }
    @GetMapping("/get-out-standing-report/{startDate}/{endDate}")
   public ResponseList getOutStandingReport(@PathVariable String startDate,@PathVariable String endDate) throws ParseException{
        return reportService.getOutStandingReport(startDate,endDate);
   }
}
