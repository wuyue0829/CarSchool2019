package com.pdkj.carschool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.QuestionRecord;
import com.pdkj.carschool.utils.ActivityUtils;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;

import java.util.ArrayList;

import crossoverone.statuslib.StatusUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.lijunguan.imgselector.ImageSelector;

public class MineActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_return;
    private ImageView im_shouchang;
    private ImageView im_cuoti;
    private ImageView im_mine;
    private ImageView im_record;
    private TextView tv_name;
    private TextView tv_schoolName;
    private TextView tv_typeName;
    private RelativeLayout rl_login_out;
    private CircleImageView im_head;


    @Override
    protected int getLayout() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        rl_return = findViewById(R.id.rl_return);
        im_shouchang = findViewById(R.id.im_shouchang);
        im_cuoti = findViewById(R.id.im_cuoti);
        im_head = findViewById(R.id.im_head);
        im_mine = findViewById(R.id.im_mine);
        tv_name = findViewById(R.id.tv_name);
        im_record = findViewById(R.id.im_record);
        tv_schoolName = findViewById(R.id.tv_schoolName);
        tv_typeName = findViewById(R.id.tv_typeName);
        rl_login_out = findViewById(R.id.rl_login_out);
        im_head = findViewById(R.id.im_head);
    }

    @Override
    protected void initEvent() {
        rl_return.setOnClickListener(this);
        im_shouchang.setOnClickListener(this);
        im_cuoti.setOnClickListener(this);
        rl_login_out.setOnClickListener(this);
        im_head.setOnClickListener(this);
        im_mine.setOnClickListener(this);
        im_record.setOnClickListener(this);
        tv_name.setText(SharedPreferenceUtils.getStringValue(mContext,"userName"));
        tv_schoolName.setText(SharedPreferenceUtils.getStringValue(mContext,"schoolName"));
        switch (Integer.parseInt(SharedPreferenceUtils.getStringValue(mContext,"examTypeId"))){
            case 1:
                tv_typeName.setText(ExamTypeId.普货资格证.toString());
                break;
            case 2:
                tv_typeName.setText(ExamTypeId.客运资格证.toString());
                break;
            case 3:
                tv_typeName.setText(ExamTypeId.危货资格证.toString());
                break;
            case 4:
                tv_typeName.setText(ExamTypeId.押运资格证.toString());
                break;
            case 5:
                tv_typeName.setText(ExamTypeId.出租车资格证.toString());
                break;
            case 6:
                tv_typeName.setText(ExamTypeId.网约车资格证.toString());
                break;
            case 7:
                tv_typeName.setText(ExamTypeId.再教育.toString());
                break;
        }

    }

    public enum ExamTypeId {
        普货资格证,客运资格证,危货资格证,押运资格证,出租车资格证,网约车资格证,再教育
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_return:
                finish();
                break;
            case R.id.im_shouchang:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type",1);
                startActivity(bundle3,MyCollectionsActivity.class);
                break;
            case R.id.im_cuoti:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type",1);
                startActivity(bundle1,MyErrorSubjectActivity.class);
                break;
            case R.id.rl_login_out:
                StyledDialog.buildIosAlert("提示", "您确定要退出登录吗？", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        SharedPreferenceUtils.clearSP(mContext);
                        ActivityUtils.removeAllActivity();
                        startActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onSecond() {
                    }
                }).show();
                break;
            case R.id.im_head:
                ImageSelector.getInstance()
                        .setSelectModel(ImageSelector.MULTI_MODE)
                        .setMaxCount(1)
                        .setGridColumns(3)
                        .setShowCamera(true)
                        .setToolbarColor(getResources().getColor(R.color.color_048ae9))
                        .startSelect(this);
                break;
            case R.id.im_mine:
                startActivity(MineMessageActivity.class);
                break;
            case R.id.im_record:
                startActivity(QustionListActivity.class);
                break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.REQUEST_SELECT_IMAGE
                && resultCode == RESULT_OK) {
            ArrayList<String> imagesPath = data.getStringArrayListExtra(ImageSelector.SELECTED_RESULT);
            if(imagesPath != null){
                SysConfig.getConfig(mContext).setHeadImage(imagesPath.get(0));
                Glide.with(mContext).load(imagesPath.get(0)).into(im_head);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(SysConfig.getConfig(mContext).getHeadImage())){
            Glide.with(mContext).load(SysConfig.getConfig(mContext).getHeadImage()).into(im_head);
        }
    }
}
