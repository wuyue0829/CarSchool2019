package com.pdkj.carschool.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.activity.base.BaseFragment;
import com.pdkj.carschool.activity.base.MyBaseActivity;
import com.pdkj.carschool.adapter.MyFragmentPagerAdapter;
import com.pdkj.carschool.databinding.ActivityQuestionsBinding;
import com.pdkj.carschool.fragments.SubjectFragment;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends BaseActivity<ActivityQuestionsBinding> {

    private String[] tabTitle= {"科目一","科目四"};

    @Override
    protected int getLayout() {
        return R.layout.activity_questions;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String userId = getIntent().getExtras().getString("userId");
        String userName = getIntent().getExtras().getString("userName");
        String userHead = getIntent().getExtras().getString("userHead");
        SharedPreferenceUtils.saveParams(this,"userId",userId);
        SharedPreferenceUtils.saveParams(this,"userName",userName);
        SharedPreferenceUtils.saveParams(this,"userHead",userHead);
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(SubjectFragment.newInstance(1));
        fragmentList.add(SubjectFragment.newInstance(2));
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter
                (getSupportFragmentManager(),fragmentList);
        mBinding.viewpager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        setTopLeftButton(R.mipmap.back_img, v -> finish());
        mBinding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (StringUtil.isEqual(tab.getText(),tabTitle[0])){
                    mBinding.viewpager.setCurrentItem(0,false);
                }else {
                    mBinding.viewpager.setCurrentItem(1,false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
