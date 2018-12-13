package com.pdkj.carschool.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.CategoriesBean;
import com.pdkj.carschool.bean.TokenModel;
import com.pdkj.carschool.utils.NetConstant;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.widgets.Item_chinder;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import crossoverone.statuslib.StatusUtil;

public class ChooseActivity extends BaseActivity {


    private LinearLayout ly_content;
    private RelativeLayout rl_return;
    private int questionType;
    private int clickType;

    @Override
    protected int getLayout() {
        return R.layout.activity_choose;
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
        rl_return.setOnClickListener(v -> finish());
        clickType = getIntent().getExtras().getInt("clickType");
        RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/examquestion/categoryList");
        TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
        params.addBodyParameter("model",new Gson().toJson(tokenModel));
        params.addBodyParameter("examTypeId", SharedPreferenceUtils.getStringValue(this,"examTypeId"));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CategoriesBean categoriesBean = new Gson().fromJson(result,CategoriesBean.class);
                if(categoriesBean.getCode() == 200){
                    ly_content.removeAllViews();
                    for(CategoriesBean.Categories categories :categoriesBean.getData()){
                        ly_content.addView(new Item_chinder(mContext,categories.getName(),categories.getId()+"",clickType));
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(cex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
