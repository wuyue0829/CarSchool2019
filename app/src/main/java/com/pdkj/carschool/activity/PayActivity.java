package com.pdkj.carschool.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.bean.LoginBeanCla;
import com.pdkj.carschool.bean.TokenModel;
import com.pdkj.carschool.utils.NetConstant;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.SysConfig;
import com.tsy.sdk.pay.alipay.Alipay;
import com.tsy.sdk.pay.weixin.WXPay;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class PayActivity extends BaseActivity {


    private TextView btn_pay;
    private String payType;
    private RelativeLayout rl_wx;
    private RelativeLayout rl_zfb;
    private ImageView im_wx;
    private ImageView im_zfb;
    private String resultParem;
    private String orderNumber;

    @Override
    protected int getLayout() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        btn_pay = findViewById(R.id.btn_pay);
        rl_wx = findViewById(R.id.rl_wx);
        rl_zfb = findViewById(R.id.rl_zfb);
        im_wx = findViewById(R.id.im_wx);
        im_zfb = findViewById(R.id.im_zfb);
        payType = "1";
    }

    @Override
    protected void initEvent() {
        btn_pay.setOnClickListener(v -> {
            payMemory();
        });
        rl_wx.setOnClickListener(v -> {
            payType = "1";
            im_wx.setImageDrawable(getResources().getDrawable(R.mipmap.icon_checkd));
            im_zfb.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ido));
        });

        rl_zfb.setOnClickListener(v -> {
            payType = "2";
            im_zfb.setImageDrawable(getResources().getDrawable(R.mipmap.icon_checkd));
            im_wx.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ido));
        });

    }

    private void payMemory(){
        if(TextUtils.isEmpty(SharedPreferenceUtils.getStringValue(mContext,"pirce"))){
            StyledDialog.buildLoading("提交订单中...");
            RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/usermember/getPrice");
            TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
            params.addBodyParameter("model",new Gson().toJson(tokenModel));
            params.addBodyParameter("examTypeId", SharedPreferenceUtils.getParams("examTypeId",this));
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    LoginBeanCla loginBeanCla = new Gson().fromJson(result,LoginBeanCla.class);
                    SharedPreferenceUtils.saveStringValue(mContext,"pirce",loginBeanCla.getData());
                    RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/payment/aliPay");
                    TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
                    params.addBodyParameter("model",new Gson().toJson(tokenModel));
                    params.addBodyParameter("examTypeId", SharedPreferenceUtils.getParams("examTypeId",mContext));
                    params.addBodyParameter("payPrice", SharedPreferenceUtils.getStringValue(mContext,"pirce"));
                    params.addBodyParameter("payWay", payType);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            resultParem = new Gson().fromJson(result, LoginBeanCla.class).getData();
                            LogUtil.e(resultParem);
                            if(payType.equals("1")){
                                doWXPay(resultParem);
                            }else{
                                doAlipay(resultParem,new Gson().fromJson(result, LoginBeanCla.class).getMessage());
                            }
                            StyledDialog.dismissLoading();
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
                            StyledDialog.dismissLoading();
                        }
                    });
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
                    StyledDialog.dismissLoading();
                }
            });
        }else{
            StyledDialog.buildLoading("跳转支付中...");
            RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/payment/aliPay");
            TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
            params.addBodyParameter("model",new Gson().toJson(tokenModel));
            params.addBodyParameter("examTypeId", SharedPreferenceUtils.getParams("examTypeId",this));
            params.addBodyParameter("payPrice", SharedPreferenceUtils.getStringValue(mContext,"pirce"));
            params.addBodyParameter("payWay", payType);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    resultParem = new Gson().fromJson(result, LoginBeanCla.class).getData();
                    LogUtil.e(resultParem);
                    if(payType.equals("1")){
                        doWXPay(resultParem);
                    }else{
                        doAlipay(resultParem,new Gson().fromJson(result, LoginBeanCla.class).getMessage());
                    }
                    StyledDialog.dismissLoading();
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
                    StyledDialog.dismissLoading();
                }
            });
        }
    }


    /**
     * 微信支付
     * @param pay_param 支付服务生成的支付参数
     */
    private void doWXPay(String pay_param) {
        String wx_appid = "wxde8dff189cd054d5";
        WXPay.init(getApplicationContext(), wx_appid);
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
                SysConfig.getConfig(mContext).setUserVip("99", SharedPreferenceUtils.getParams("examTypeId",mContext));
                finish();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        Toast.makeText(getApplication(), "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        Toast.makeText(getApplication(), "参数错误", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }






    /**
     * 支付宝支付
     * @param pay_param 支付服务生成的支付参数
     */
    private void doAlipay(String pay_param,String orderNumberStr) {

        new Alipay(this, pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {


                RequestParams params = new RequestParams(NetConstant.BASE_URL + "/app/payment/payNotify");
                TokenModel tokenModel = new TokenModel(SharedPreferenceUtils.getIntValue(mContext,"userId"),SharedPreferenceUtils.getParams("token",mContext));
                params.addBodyParameter("model",new Gson().toJson(tokenModel));
                params.addBodyParameter("status", "1");
                params.addBodyParameter("orderNumber", orderNumberStr);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
                        SysConfig.getConfig(mContext).setUserVip("99", SharedPreferenceUtils.getParams("examTypeId",mContext));
                        finish();
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
                        StyledDialog.dismissLoading();
                    }
                });
            }

            @Override
            public void onDealing() {
                Toast.makeText(getApplication(), "支付处理中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        Toast.makeText(getApplication(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_NETWORK:
                        Toast.makeText(getApplication(), "支付失败:网络连接错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getApplication(), "支付错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        }).doPay();
    }
}
