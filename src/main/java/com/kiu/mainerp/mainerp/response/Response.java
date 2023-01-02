package com.kiu.mainerp.mainerp.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private int code;
    private String msg;
    private Object data;

}
