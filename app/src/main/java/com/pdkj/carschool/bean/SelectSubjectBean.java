package com.pdkj.carschool.bean;

import java.io.Serializable;

public class SelectSubjectBean implements Serializable {

    private int SelectStatus;//题目状态 1 正确 2 错误 3 未做题目
    private String subject;//第几题

    public int getSelectStatus() {
        return SelectStatus;
    }

    public void setSelectStatus(int selectStatus) {
        SelectStatus = selectStatus;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
