package com.pdkj.carschool.bean;


import java.util.List;

public class QustionBean {

    private int code;
    private List<Qustion> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Qustion> getData() {
        return data;
    }

    public void setData(List<Qustion> data) {
        this.data = data;
    }
}
