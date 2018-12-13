package com.pdkj.carschool.activity.base;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;

/**
 * Created by 11470 on 2017/10/23.
 */

public abstract class MyBaseActivity<V extends ViewDataBinding> extends BaseActivity<V> {
    public Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
}