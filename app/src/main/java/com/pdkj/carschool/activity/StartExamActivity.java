package com.pdkj.carschool.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.activity.base.MyBaseActivity;
import com.pdkj.carschool.databinding.ActivityStartBinding;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 开始模拟考试
 */
public class StartExamActivity extends BaseActivity<ActivityStartBinding> {

    private int type;// 1 科目一  2 科目四
    private TextView tv_car_type;
    private ImageView iv_user_head_question_start;
    private TextView tv_time;
    private TextView tv_hege;
    private String mId;
    private int model1;
    private int model2;
    private int model3;
    private int model4 = 0;
    private int model5 = 0;
    private int model6 = 0;
    private int time;
    private int manfen;
    private String name;

    @Override
    protected int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mId = getIntent().getExtras().getString("mId");
        tv_car_type = findViewById(R.id.tv_car_type);
        iv_user_head_question_start = findViewById(R.id.iv_user_head_question_start);
        tv_time = findViewById(R.id.tv_time);
        tv_hege = findViewById(R.id.tv_hege);
        type = getIntent().getExtras().getInt("type");
        name = getIntent().getExtras().getString("name");
        setTitle("模拟考试");
        String userHead = SharedPreferenceUtils.getParams("userHead",this);
        String userName = SharedPreferenceUtils.getParams("userName",this);
        Glide.with(getApplicationContext()).load(userHead)
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .placeholder(R.mipmap.defult_img)
                .error(R.mipmap.defult_img)
                .into(mBinding.ivUserHeadQuestionStart);
        mBinding.tvUserNicknameQuestionStart.setText(userName);
        if(!TextUtils.isEmpty(SysConfig.getConfig(mContext).getHeadImage())){
            Glide.with(mContext).load(SysConfig.getConfig(mContext).getHeadImage()).into(iv_user_head_question_start);
        }

        switch (Integer.parseInt(SharedPreferenceUtils.getStringValue(mContext,"examTypeId"))){
            case 1:
                tv_car_type.setText(MineActivity.ExamTypeId.普货资格证.toString());
                tv_time.setText("理论:60分钟\n危险源识别及节能驾驶:30分钟");
                tv_hege.setText("理论:满分100，80及格\n危险源识别及节能驾驶:满分30，18分及格");
                if(name.equals("理论") || name.equals("理论,")){
                    model1 = 20;
                    model2 = 20;
                    model3 = 25;
                    model6 = 20;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",80);
                }else{
                    model4 = 10;
                    model5 = 5;
                    time = 30;
                    manfen = 30;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",18);
                }
                break;
            case 2:
                tv_car_type.setText(MineActivity.ExamTypeId.客运资格证.toString());
                tv_time.setText("理论:60分钟\n危险源识别及节能驾驶:30分钟");
                tv_hege.setText("理论:满分100，80及格\n危险源识别及节能驾驶:满分30，18分及格");
                if(name.equals("理论") || name.equals("理论,")){
                    model1 = 40;
                    model2 = 40;
                    model3 = 10;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",80);
                }else{
                    model4 = 10;
                    model5 = 5;
                    time = 30;
                    manfen = 30;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",18);
                }
                break;
            case 3:
                tv_car_type.setText(MineActivity.ExamTypeId.危货资格证.toString());
                tv_time.setText("基础篇:60分钟\n爆炸篇:60分钟\n剧毒篇:60分钟");
                tv_hege.setText("基础篇:满分100，90及格\n爆炸篇:满分100，90及格\n剧毒篇:满分100，90及格");
                if(name.equals("基础篇")){
                    model1 = 50;
                    model2 = 50;
                    model3 = 0;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",90);
                }else if(name.equals("爆炸篇")){
                    model1 = 50;
                    model2 = 50;
                    model3 = 0;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",90);
                }else{
                    model1 = 50;
                    model2 = 50;
                    model3 = 0;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",90);
                }
                break;
            case 4:
                tv_car_type.setText(MineActivity.ExamTypeId.押运资格证.toString());
                tv_time.setText("基础篇:60分钟\n爆炸篇:60分钟\n剧毒篇:60分钟");
                tv_hege.setText("基础篇:满分100，90及格\n爆炸篇:满分100，90及格\n剧毒篇:满分100，90及格");
                if(name.equals("基础篇")){
                    model1 = 50;
                    model2 = 50;
                    model3 = 0;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",90);
                }else if(name.equals("爆炸篇")){
                    model1 = 50;
                    model2 = 50;
                    model3 = 0;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",90);
                }else{
                    model1 = 50;
                    model2 = 50;
                    model3 = 0;
                    model6 = 0;
                    time = 60;
                    manfen = 100;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",90);
                }
                break;
            case 5:
                tv_car_type.setText(MineActivity.ExamTypeId.出租车资格证.toString());
                tv_time.setText("公共题:50分钟\n区域题（运城）:30分钟");
                tv_hege.setText("公共题:满分50，40及格\n区域题（运城）:满分30，24分及格");
                if(name.equals("公共题")){
                    model1 = 25;
                    model2 = 25;
                    model3 = 0;
                    model6 = 0;
                    time = 50;
                    manfen = 50;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",40);
                }else{
                    model1 = 15;
                    model2 = 15;
                    model3 = 0;
                    model6 = 0;
                    time = 50;
                    manfen = 50;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",24);
                }
                break;
            case 6:
                tv_car_type.setText(MineActivity.ExamTypeId.网约车资格证.toString());
                tv_time.setText("公共题:50分钟\n区域题（运城）:50分钟");
                tv_hege.setText("公共题:满分50，40及格\n区域题（运城）:满分50，40分及格");
                if(name.equals("公共题")){
                    model1 = 25;
                    model2 = 25;
                    model3 = 0;
                    model6 = 0;
                    time = 50;
                    manfen = 50;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",40);
                }else{
                    model1 = 25;
                    model2 = 25;
                    model3 = 0;
                    model6 = 0;
                    time = 50;
                    manfen = 50;
                    SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",40);
                }
                break;
            case 7:
                tv_car_type.setText(MineActivity.ExamTypeId.再教育.toString());
                tv_time.setText("理论:60分钟\n危险源识别及节能驾驶:30分钟");
                tv_hege.setText("理论:满分100，80及格\n危险源识别及节能驾驶:满分30，18分及格");
                model1 = 50;
                model2 = 50;
                model3 = 0;
                model6 = 0;
                time = 60;
                manfen = 100;
                SysConfig.getConfig(mContext).setCustomConfigInt("lastScart",80);
                break;
        }
        mBinding.tvExamNumQuestionStart.setText("您今天还没开始模拟考试");

    }

    @Override
    protected void initEvent() {
        setTopLeftButton(R.mipmap.back_img,v -> finish());
        mBinding.btnStartQuestions.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("questionType", 1);// 1 模拟考试 2 章节练习
            bundle.putInt("model1",model1);
            bundle.putInt("model2",model2);
            bundle.putInt("model3",model3);
            bundle.putInt("model4",model4);
            bundle.putInt("model5",model5);
            bundle.putInt("model6",model6);
            bundle.putInt("time",time);
            bundle.putInt("manfen",manfen);
            bundle.putInt("type", type);
            bundle.putString("mId", mId);
            startActivity(bundle, SubjectActivity.class);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
