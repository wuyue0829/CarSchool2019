package com.pdkj.carschool.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class version extends BmobObject {

    private Integer versionCode;
    private BmobFile download;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public BmobFile getDownload() {
        return download;
    }

    public void setDownload(BmobFile download) {
        this.download = download;
    }
}
