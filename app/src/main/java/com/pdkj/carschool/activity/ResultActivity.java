package com.pdkj.carschool.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.QuestionRecord;
import com.pdkj.carschool.databinding.ActivityResultBinding;
import com.pdkj.carschool.utils.DateUtils;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

public class ResultActivity extends BaseActivity<ActivityResultBinding> {

    private String time;
    private int type;
    private int source;
    private TextView tv_car_type_result;
    private TextView tv_to_subject;

    @Override
    protected int getLayout() {
        return R.layout.activity_result;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        time = bundle.getString("time1");
        type = bundle.getInt("subjectType");
        source = bundle.getInt("source");
        tv_car_type_result = findViewById(R.id.tv_car_type_result);
        tv_to_subject = findViewById(R.id.tv_to_subject);
        setTitle("考试结果");

        switch (Integer.parseInt(SharedPreferenceUtils.getStringValue(mContext,"examTypeId"))){
            case 1:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.普货资格证.toString());
                break;
            case 2:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.客运资格证.toString());
                break;
            case 3:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.危货资格证.toString());
                break;
            case 4:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.押运资格证.toString());
                break;
            case 5:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.出租车资格证.toString());
                break;
            case 6:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.网约车资格证.toString());
                break;
            case 7:
                mBinding.tvQuestionTypeResult.setText(MineActivity.ExamTypeId.再教育.toString());
                break;
        }

        tv_car_type_result.setText(SysConfig.getConfig(mContext).getCustomConfig("nameones",""));
        mBinding.tvTimeResult.setText(time);
        mBinding.tvResultSource.setText(""+source);
        if (source < SysConfig.getConfig(mContext).getCustomConfigInt("lastScart")){
            mBinding.tvResultSource.setTextColor(getResources().getColor(R.color.color_f4011f));
            mBinding.lvnResultBack.setImageResource(R.mipmap.result_no_img);
            mBinding.tvResultText1.setText("马路杀手");
            mBinding.tvResultText1.setTextColor(getResources().getColor(R.color.color_f4011f));
            mBinding.tvResultText2.setText("不要灰心，下次继续加油啦！");
        }

        DbManager db = x.getDb(PDApplication.getDaoConfig());
        QuestionRecord questionRecord = new QuestionRecord();
        questionRecord.setQuestionTime(DateUtils.getNow());
        questionRecord.setScore(source+"");
        try {
            db.save(questionRecord);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent() {
        setTopLeftButton(R.mipmap.back_img, v -> finish());
        mBinding.tvMyErrorResult.setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putInt("type",type);
            startActivity(bundle,MyErrorSubjectActivity.class);
            finish();
        });
        tv_to_subject.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("questionType", 1);// 1 模拟考试 2 章节练习
            bundle.putInt("type", type);
            bundle.putInt("model1",getIntent().getExtras().getInt("model1"));
            bundle.putInt("model2",getIntent().getExtras().getInt("model2"));
            bundle.putInt("model3",getIntent().getExtras().getInt("model3"));
            bundle.putInt("model4",getIntent().getExtras().getInt("model4"));
            bundle.putInt("model5",getIntent().getExtras().getInt("model5"));
            bundle.putInt("time",getIntent().getExtras().getInt("time"));
            bundle.putInt("manfen",getIntent().getExtras().getInt("manfen"));
            bundle.putString("mId", getIntent().getExtras().getString("mId"));
            startActivity(bundle, SubjectActivity.class);
            finish();
        });
    }
}
