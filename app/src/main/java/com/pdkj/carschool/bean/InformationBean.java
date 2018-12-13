package com.pdkj.carschool.bean;

public class InformationBean {

    //考试类型  1
    private int examTypeId;


    //所属驾校
    private String schoolName;

    /**
     * 姓名
     */
    private String name;

    public void setExamTypeId(int examTypeId) {
        this.examTypeId = examTypeId;
    }

    public int getExamTypeId() {
        return examTypeId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
