package com.kiu.mainerp.mainerp.models.out_standing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OutstandingResponseModel {
    @JsonProperty("data_list")
private List<OutstandingDataModel> dataList;
@JsonProperty("total_due_amount")
private  Double totalDueAmount;
}
