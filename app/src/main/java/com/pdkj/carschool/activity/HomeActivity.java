package com.pdkj.carschool.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.activity.base.BaseHandler;
import com.pdkj.carschool.activity.base.MyBaseActivity;
import com.pdkj.carschool.bean.IsPayMemory;
import com.pdkj.carschool.bean.Qustion;
import com.pdkj.carschool.bean.QustionBean;
import com.pdkj.carschool.bean.TokenModel;
import com.pdkj.carschool.bean.isupdatecode;
import com.pdkj.carschool.utils.DateUtils;
import com.pdkj.carschool.utils.NetConstant;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import crossoverone.statuslib.StatusUtil;

import static org.xutils.common.util.LogUtil.e;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView im_lianxi;
    private ImageView im_moni;
    private ImageView im_tiku;
    private RelativeLayout rl_mine;
    private RelativeLayout rl_return;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new BaseHandler(this) {
        @Override
        protected void handleMessage(Context mContext, Message msg) {
            StyledDialog.dismissLoading();
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        StatusUtil.setUseStatusBarColor(this, Color.TRANSPARENT, Color.parseColor("#33000000"));
        StatusUtil.setSystemStatus(this, true, true);
        im_lianxi =  findViewById(R.id.im_lianxi);
        im_moni =  findViewById(R.id.im_moni);
        im_tiku =  findViewById(R.id.im_tiku);
        rl_mine = findViewById(R.id.rl_mine);
        rl_return  =  findViewById(R.id.rl_return);
    }

    @Override
    protected void initEvent() {
        im_lianxi.setOnClickListener(this);
        im_moni.setOnClickListener(this);
        im_tiku.setOnClickListener(this);
        rl_mine.setOnClickListener(this);
        rl_return.setOnClickListener(this);


        if(TextUtils.isEmpty(SharedPreferenceUtils.getParams("examTypeId",mContext))||SharedPreferenceUtils.getParams("examTypeId",mContext).equals("0")){
            StyledDialog.buildIosAlert("提示", "您还没有完善个人信息，请先去完善信息！", new MyDialogListener() {
                @Override
                public void onFirst() {
                    startActivity(InformationActivity.class);
                }

                @Override
                public void onSecond() {
                    finish();
                }
            }).show();
        }else{
            /**
             * 获取是否为付费用户
             */
            if(!TextUtils.isEmpty(SharedPreferenceUtils.getParams("examTypeId",this))){
                RequestParams params1 = new RequestParams(NetConstant.BASE_URL + "/app/usermember/isPayment");
                TokenModel tokenMode2 = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
                params1.addBodyParameter("model",new Gson().toJson(tokenMode2));
                params1.addBodyParameter("examTypeId", SharedPreferenceUtils.getParams("examTypeId",mContext));
                x.http().post(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        IsPayMemory isPayMemory = new Gson().fromJson(result, IsPayMemory.class);
                        if(isPayMemory.isData()){
                            SysConfig.getConfig(mContext).setUserVip("99", SharedPreferenceUtils.getParams("examTypeId",mContext));
                        }
                        SharedPreferenceUtils.saveBooleanValue(mContext,"isVip",isPayMemory.isData());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {

                    }
                });

                StyledDialog.buildLoading("正在检查题库是否有新版本").show();
                /**
                 * 获取试题保存到数据库
                 */
                RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/examquestion/lastQuestion");
                TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
                params.addBodyParameter("model",new Gson().toJson(tokenModel));
                params.setConnectTimeout(100000);
                params.setReadTimeout(100000);
                params.addBodyParameter("examTypeId", SharedPreferenceUtils.getParams("examTypeId",mContext));
                params.addBodyParameter("lastDate", SysConfig.getConfig(mContext).getLastTime(SharedPreferenceUtils.getParams("examTypeId",mContext)));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        QustionBean qustionBean = new Gson().fromJson(result,QustionBean.class);
                        if(qustionBean.getCode() == 200){
                            if(null != qustionBean.getData() && qustionBean.getData().size()>0){
                                StyledDialog.buildLoading("试题库正在更新中，请不要退出或者切换到后台").show();
                                new Thread(() -> {
                                    DbManager db = x.getDb(PDApplication.getDaoConfig());
                                    List<Qustion> qustionList = new ArrayList<>();
                                    for(Qustion qustion:qustionBean.getData()){

                                        Qustion qustion1 = new Qustion();
                                        qustion1.setAnswer(qustion.getAnswer());
                                        qustion1.setCategoryId(qustion.getCategoryId());
                                        qustion1.setExamTypeId(qustion.getExamTypeId());
                                        qustion1.setId(qustion.getId());
                                        qustion1.setScene(qustion.isScene());
                                        qustion1.setQuestion(qustion.getQuestion());
                                        qustion1.setQuestionType(qustion.getQuestionType());
                                        qustion1.setRemark(qustion.getRemark());

                                        qustion1.setLastDate(qustion.getLastDate());
                                        if(!TextUtils.isEmpty(qustion.getUrl())){
                                            qustion1.setUrl(NetConstant.BASE_URL+qustion.getUrl());
                                        }else{
                                            qustion1.setUrl("");
                                        }
                                        qustion1.setCategoryType(qustion.getCategoryType());
                                        if(qustion.getOptions().size() == 1){
                                            qustion1.setItem1(qustion.getOptions().get(0).getOptionName());
                                            qustion1.setItem2("");
                                            qustion1.setItem3("");
                                            qustion1.setItem4("");
                                        }
                                        if(qustion.getOptions().size() == 2){
                                            qustion1.setItem1(qustion.getOptions().get(0).getOptionName());
                                            qustion1.setItem2(qustion.getOptions().get(1).getOptionName());
                                            qustion1.setItem3("");
                                            qustion1.setItem4("");
                                        }
                                        if(qustion.getOptions().size() == 3){
                                            qustion1.setItem1(qustion.getOptions().get(0).getOptionName());
                                            qustion1.setItem2(qustion.getOptions().get(1).getOptionName());
                                            qustion1.setItem3(qustion.getOptions().get(2).getOptionName());
                                            qustion1.setItem4("");
                                        }
                                        if(qustion.getOptions().size() == 4){
                                            qustion1.setItem1(qustion.getOptions().get(0).getOptionName());
                                            qustion1.setItem2(qustion.getOptions().get(1).getOptionName());
                                            qustion1.setItem3(qustion.getOptions().get(2).getOptionName());
                                            qustion1.setItem4(qustion.getOptions().get(3).getOptionName());
                                        }
                                        qustionList.add(qustion1);
                                    }

                                    try {
                                        for(Qustion qustion :qustionList){
                                            if(null != db.findById(Qustion.class,qustion.getId())){
                                                db.update(qustion);
                                            }else{
                                                db.save(qustion);
                                            }
                                        }
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                    if(null != qustionList && qustionList.size()>0){
                                        SysConfig.getConfig(mContext).setLastTime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm:ss",qustionList.get(0).getLastDate()),SharedPreferenceUtils.getParams("examTypeId",mContext));
                                    }
                                    mHandler.sendEmptyMessage(2000);
                                }).start();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        e(ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        e(cex.getMessage());
                    }

                    @Override
                    public void onFinished() {
                        StyledDialog.dismissLoading();
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_lianxi:
                Bundle bundle = new Bundle();
                bundle.putInt("questionType",2);
                bundle.putString("examTypeId",SharedPreferenceUtils.getStringValue(this,"examTypeId"));
                bundle.putInt("type",1);
                bundle.putInt("clickType",1);
                startActivity(bundle, ChooseActivity.class);
                break;
            case R.id.im_moni:
                Bundle bundle1 = new Bundle();
                bundle1.putString("examTypeId",SharedPreferenceUtils.getStringValue(this,"examTypeId"));
                bundle1.putInt("type",1);
                bundle1.putInt("clickType",2);
                startActivity(bundle1, ChooseActivity.class);
                break;
            case R.id.im_tiku:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type",1);
                startActivity(bundle3,MyCollectionsActivity.class);

                break;
            case R.id.rl_mine:
                startActivity(MineActivity.class);
                break;
            case R.id.rl_return:
                finish();
                break;
        }
    }


}
