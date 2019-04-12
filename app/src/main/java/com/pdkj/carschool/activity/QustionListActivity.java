package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hss01248.dialog.StyledDialog;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.QuestionRecord;
import com.pdkj.carschool.widgets.Item_Qustion;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import crossoverone.statuslib.StatusUtil;

public class QustionListActivity extends BaseActivity {
    private LinearLayout ly_content;
    private RelativeLayout rl_return;

    @Override
    protected int getLayout() {
        return R.layout.activity_qustion_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        ly_content = findViewById(R.id.ly_content);
        rl_return = findViewById(R.id.rl_return);

    }

    @Override
    protected void initEvent() {

        rl_return.setOnClickListener(view -> finish());
        StyledDialog.buildLoading("获取数据中...");
        DbManager db = x.getDb(PDApplication.getDaoConfig());
        try {
            List<QuestionRecord> lyjPersons= db.selector(QuestionRecord.class).findAll();
            if(lyjPersons != null &&lyjPersons.size()>0){
                ly_content.removeAllViews();
                for (int i=0;i< lyjPersons.size();i++){
                    ly_content.addView(new Item_Qustion(mContext,lyjPersons.get(i).getQuestionTime(),lyjPersons.get(i).getScore()));
                }
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        StyledDialog.dismissLoading();
    }
}
