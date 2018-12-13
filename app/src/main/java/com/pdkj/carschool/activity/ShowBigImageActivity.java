package com.pdkj.carschool.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.databinding.ActivityShowBigImageBinding;
import com.pdkj.carschool.utils.StringUtil;

public class ShowBigImageActivity extends BaseActivity<ActivityShowBigImageBinding> {

    @Override
    protected int getLayout() {
        return R.layout.activity_show_big_image;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String url = getIntent().getExtras().getString("url");
        if (StringUtil.isNotEmpty(url)){
            Glide.with(this).load(url).error(R.mipmap.defult_img).into(mBinding.ivShowImage);
        }
    }

    @Override
    protected void initEvent() {

    }

}
