package com.pdkj.carschool.bean;

import java.util.List;

public class CategoriesBean {

    private int code;
    private List<Categories> data;


    public class Categories{
        private int examTypeId;
        private String name;
        private String remark;
        private int id;

        public int getExamTypeId() {
            return examTypeId;
        }

        public void setExamTypeId(int examTypeId) {
            this.examTypeId = examTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Categories> getData() {
        return data;
    }

    public void setData(List<Categories> data) {
        this.data = data;
    }
}
