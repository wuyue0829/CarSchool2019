package com.pdkj.carschool.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.base.BaseActivity;
import com.pdkj.carschool.activity.base.BaseFragment;
import com.pdkj.carschool.activity.base.MyBaseActivity;
import com.pdkj.carschool.adapter.MyFragmentPagerAdapter;
import com.pdkj.carschool.bean.QuestionsBean;
import com.pdkj.carschool.bean.Qustion;
import com.pdkj.carschool.bean.SelectSubjectBean;
import com.pdkj.carschool.databinding.ActivitySubjectBinding;
import com.pdkj.carschool.db.QuestionsMetaData;
import com.pdkj.carschool.db.QuestionsSqlBrite;
import com.pdkj.carschool.fragments.JudgeFragment;
import com.pdkj.carschool.fragments.MultiselectFragment;
import com.pdkj.carschool.fragments.RadioFragment;
import com.pdkj.carschool.utils.FirstClickUtils;
import com.pdkj.carschool.utils.MyLog;
import com.pdkj.carschool.utils.MyUtils;
import com.pdkj.carschool.utils.SharedPreferenceUtils;
import com.pdkj.carschool.utils.StringUtil;
import com.pdkj.carschool.utils.SysConfig;
import com.pdkj.carschool.widgets.ResultDialog;
import com.pdkj.carschool.widgets.SelectSubjectPopupWindow;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * 答题
 */
public class SubjectActivity extends BaseActivity<ActivitySubjectBinding> implements
        RadioFragment.RadioSelectorSubject, JudgeFragment.JudgeSelectorSubject, MultiselectFragment.MultSelectorSubject {

    private ArrayList<QuestionsBean> dataList;
    private int questionType;
    private int successNum;//对对题数
    private double successNum1;//单选对题数
    private double successNum2;//判断对题数
    private double successNum3;//多选对题数
    private int successNum4;//图像题对题数
    private int failNum;//错题数
    private int failNum1;//错题数
    private int noWriteNum;//未做题目
    private int subject1Count = 100;//总题数
    private final int subject4Count = 50;//总题数
    private List<BaseFragment> fragmentList;
    private LinearLayout lvn_time_subject;
    private int lastAnswer = 0;
    private QuestionsSqlBrite sqlBrite;
    private int type;
    private SelectSubjectPopupWindow window;
    private String categoryId;
    private String[] str;
    private String name;
    private int model1;
    private int model2;
    private int model3;
    private int model4;
    private int model5;
    private int model6 = 0;
    private int time;
    private int manfen;
    private DbManager db = x.getDb(PDApplication.getDaoConfig());

    private long questionTime = -26100000;// 1970 45分钟

    private Runnable runnable1 = () -> {
        ArrayList<QuestionsBean> beenList = new ArrayList<>();
        List<Qustion> persons = new ArrayList<>();
        List<Qustion> persons10 = new ArrayList<>();
        if(questionType == 2){
            if(name.contains("节能")){
                try {
                    persons = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("categoryType","=","危险源").findAll();
                    persons10 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("categoryType","=","节能").findAll();
                    if(persons10!= null && persons10.size()>0){
                        for(Qustion qustion:persons10){
                            persons.add(qustion);
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    persons = db.selector(Qustion.class).where("categoryId", "=", categoryId).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }else{
            if(model4>0&&model5>0){
                List<Qustion> persons1 = new ArrayList<>();
                List<Qustion> persons2 = new ArrayList<>();
                lvn_time_subject.setVisibility(View.VISIBLE);
                mBinding.chronometer.start();

                try {
                    persons1 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("categoryType","=","危险源").and("examTypeId","=", SharedPreferenceUtils.getStringValue(mContext,"examTypeId")).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if(persons1!=null&&persons1.size()>0){
                    Collections.shuffle(persons1);
                    for(int i = 0;i<model4;i++){
                        persons.add(persons1.get(i));
                    }
                }

                try {
                    persons2 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("categoryType","=","节能").and("examTypeId","=",SharedPreferenceUtils.getStringValue(mContext,"examTypeId")).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }

                if(persons2!=null&&persons2.size()>0){
                    Collections.shuffle(persons2);
                    for(int i = 0;i<model5;i++){
                        persons.add(persons2.get(i));
                    }
                }
            }else{
                lvn_time_subject.setVisibility(View.VISIBLE);
                mBinding.chronometer.start();
                List<Qustion> persons1 = new ArrayList<>();
                List<Qustion> persons2 = new ArrayList<>();
                List<Qustion> persons3 = new ArrayList<>();
                List<Qustion> persons4 = new ArrayList<>();
                try {
                    persons1 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("isScene","=","0").and("questionType","=","3").and("examTypeId","=", SharedPreferenceUtils.getStringValue(mContext,"examTypeId")).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if(persons1!=null&&persons1.size()>0){
                    Collections.shuffle(persons1);
                    for(int i = 0;i<model1;i++){
                        persons.add(persons1.get(i));
                    }
                }

                try {
                    persons2 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("isScene","=","0").and("questionType","=","1").and("examTypeId","=",SharedPreferenceUtils.getStringValue(mContext,"examTypeId")).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }

                if(persons2!=null&&persons2.size()>0){
                    Collections.shuffle(persons2);
                    for(int i = 0;i<model2;i++){
                        persons.add(persons2.get(i));
                    }
                }
                try {
                    persons3 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("isScene","=","0").and("questionType","=","2").and("examTypeId","=",SharedPreferenceUtils.getStringValue(mContext,"examTypeId")).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if(persons3!=null&&persons3.size()>0){
                    Collections.shuffle(persons3);
                    for(int i = 0;i<model3;i++){
                        persons.add(persons3.get(i));
                    }
                }
                try {
                    persons4 = db.selector(Qustion.class).where("categoryId", "=", categoryId).and("isScene","=","1").and("examTypeId","=",SharedPreferenceUtils.getStringValue(mContext,"examTypeId")).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if(persons4!=null&&persons4.size()>0){
                    Collections.shuffle(persons4);
                    for(int i = 0;i < model6;i++){
                        persons.add(persons4.get(i));
                    }
                }

            }
        }


        for(Qustion qustion:persons){
            QuestionsBean questionsBean = new QuestionsBean();
            questionsBean.setAnswer(qustion.getAnswer());
            questionsBean.setExplains(qustion.getExamTypeId()+"");
            questionsBean.setId(qustion.getId()+"");
            questionsBean.setItem1(qustion.getItem1());
            questionsBean.setItem2(qustion.getItem2());
            questionsBean.setItem3(qustion.getItem3());
            questionsBean.setItem4(qustion.getItem4());
            questionsBean.setType(qustion.getQuestionType());
            questionsBean.setUrl(qustion.getUrl());
            questionsBean.setQuestion(qustion.getQuestion());
            questionsBean.setIsScene(qustion.isScene());
            questionsBean.setExamTypeId(qustion.getExamTypeId());
            questionsBean.setCategoryId(qustion.getCategoryId());
            beenList.add(questionsBean);
        }

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", beenList);
        message.setData(bundle);
        message.what = 0x123;
        handler.sendMessage(message);
    };


    @Override
    protected void HandlerMessage(Context mContext, Message msg) {
        super.HandlerMessage(mContext, msg);
        if (msg.what == 0x125) {
            MyLog.i("设置为已收藏>>>>");
            mBinding.ivCollectionSubject.setImageResource(R.mipmap.collectionsed_img);
        } else if (msg.what == 0x124) {
            MyLog.i("设置为未收藏>>>>");
            mBinding.ivCollectionSubject.setImageResource(R.mipmap.my_collections_img);
        } else if (msg.what == 0x123 && msg.getData() != null) {
            dataList = (ArrayList<QuestionsBean>) msg.getData().getSerializable("question");
            if (questionType == 1) {
                setViewPager(dataList);
                setTitle(R.mipmap.subject_manager_img, "1/" + dataList.size(), R.mipmap.button_select_subject_img);
            } else if (questionType == 2) {
                setTitle(R.mipmap.subject_manager_img, "1/" + dataList.size(), R.mipmap.button_select_subject_img);
                setViewPager(dataList);
            }
        }
    }

    private void setViewPager(List<QuestionsBean> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            QuestionsBean bean = dataList.get(i);
            if (StringUtil.isEqual(bean.getType(), "1")) {// 1 单选 2 多选 3 判断
                RadioFragment fragment = RadioFragment.newInstance(bean,null);
                fragmentList.add(fragment);
            } else if (StringUtil.isEqual(bean.getType(), "3")) {
                JudgeFragment fragment = JudgeFragment.newInstance(bean,null);
                fragmentList.add(fragment);
            } else if (StringUtil.isEqual(bean.getType(), "2")) {
                MultiselectFragment fragment = MultiselectFragment.newInstance(bean,null);
                fragmentList.add(fragment);
            }

        }
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mBinding.viewpagerSubject.setAdapter(adapter);
        if(questionType == 2){
            if(lastAnswer > 0){
                StyledDialog.buildIosAlertVertical("提示！", "您想继续上次答题结束的题目继续答题吗？", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        mBinding.viewpagerSubject.setCurrentItem(lastAnswer);
                    }

                    @Override
                    public void onSecond() {
                        mBinding.viewpagerSubject.setCurrentItem(0);
                    }
                }).show();
            }else{
                mBinding.viewpagerSubject.setCurrentItem(0);
            }
        }
        selectIsCollections(0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_subject;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {



        /*if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("1")){
            lastAnswer = SysConfig.getConfig(mContext).getLastAnswer();
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("2")){
            lastAnswer = SysConfig.getConfig(mContext).getLastAnswer2();
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("3")){
            lastAnswer = SysConfig.getConfig(mContext).getLastAnswer3();
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("4")){
            lastAnswer = SysConfig.getConfig(mContext).getLastAnswer4();
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("5")){
            lastAnswer = SysConfig.getConfig(mContext).getLastAnswer5();
        }*/
        questionType = getIntent().getExtras().getInt("questionType");
        type = getIntent().getExtras().getInt("type");
        model1 = getIntent().getExtras().getInt("model1");
        model2 = getIntent().getExtras().getInt("model2");
        model3 = getIntent().getExtras().getInt("model3");
        model4 = getIntent().getExtras().getInt("model4");
        model5 = getIntent().getExtras().getInt("model5");
        model6 = getIntent().getExtras().getInt("model6");
        lvn_time_subject = findViewById(R.id.lvn_time_subject);
        if(model4>0&&model5>0){
            subject1Count = (model4+model5);
        }else{
            subject1Count = (model1+model2+model3+model6);
        }
        time = getIntent().getExtras().getInt("time");
        manfen = getIntent().getExtras().getInt("manfen");
        questionTime = time *60*1000;
        categoryId = getIntent().getExtras().getString("mId");
        name = getIntent().getExtras().getString("name");
        lastAnswer = SysConfig.getConfig(mContext).getCustomConfigInt("laster"+categoryId+"00000");
        str = new String[]{"categoryId"};
        sqlBrite = QuestionsSqlBrite.getSqlSingleton(this);
        fragmentList = new ArrayList<>();
        window = new SelectSubjectPopupWindow(this);

        mBinding.tvYesSubject.setText("" + successNum);
        mBinding.tvNoSubject.setText("" + failNum);

        new Thread(runnable1).start();
        mBinding.lvnTimeSubject.setVisibility(View.GONE);
        mBinding.viewpagerSubject.setNoScroll(false);
    }

    private void selectIsCollections(final int position) {
        final BaseFragment fragment = fragmentList.get(position);
        Cursor cursor = null;
        if (type == 1){
            if (fragment instanceof RadioFragment) {
                cursor = sqlBrite.rawQueryDb("select * from " + QuestionsMetaData.MetaData.TABLE_NAME_COLLECTIONS_SUBJECT1
                        + " where " + QuestionsMetaData.MetaData.ID + " = ?", new String[]{((RadioFragment) fragment).getBean().getId()});
            } else if (fragment instanceof JudgeFragment) {
                cursor = sqlBrite.rawQueryDb("select * from " + QuestionsMetaData.MetaData.TABLE_NAME_COLLECTIONS_SUBJECT1
                        + " where " + QuestionsMetaData.MetaData.ID + " = ?", new String[]{((JudgeFragment) fragment).getBean().getId()});
            } else if (fragment instanceof MultiselectFragment) {
                cursor = sqlBrite.rawQueryDb("select * from " + QuestionsMetaData.MetaData.TABLE_NAME_COLLECTIONS_SUBJECT1
                        + " where " + QuestionsMetaData.MetaData.ID + " = ?", new String[]{((MultiselectFragment) fragment).getBean().getId()});
            }
        }
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                QuestionsBean bean = QuestionsBean.getQuestionsBean(cursor);
                if (fragment instanceof RadioFragment) {
                    if (bean != null && StringUtil.isEqual(bean.getId(), ((RadioFragment) fragment).getBean().getId())) {
                        ((RadioFragment) fragment).setCollections(true);
                        handler.sendEmptyMessage(0x125);
                    }
                } else if (fragment instanceof JudgeFragment) {
                    if (bean != null && StringUtil.isEqual(bean.getId(), ((JudgeFragment) fragment).getBean().getId())) {
                        ((JudgeFragment) fragment).setCollections(true);
                        handler.sendEmptyMessage(0x125);
                    }
                } else if (fragment instanceof MultiselectFragment) {
                    if (bean != null && StringUtil.isEqual(bean.getId(), ((MultiselectFragment) fragment).getBean().getId())) {
                        ((MultiselectFragment) fragment).setCollections(true);
                        handler.sendEmptyMessage(0x125);
                    }
                }
            }
        } else {
            if (fragment instanceof RadioFragment) {//单选
                MyLog.i("查询后没有收藏>>>>");
                ((RadioFragment) fragment).setCollections(false);
                handler.sendEmptyMessage(0x124);
            } else if (fragment instanceof JudgeFragment) {//判断
                MyLog.i("查询后没有收藏>>>>");
                ((JudgeFragment) fragment).setCollections(false);
                handler.sendEmptyMessage(0x124);
            } else if (fragment instanceof MultiselectFragment) {//多选
                MyLog.i("查询后没有收藏>>>>");
                ((MultiselectFragment) fragment).setCollections(false);
                handler.sendEmptyMessage(0x124);
            }
        }

        cursor.close();
    }

    @Override
    protected void initEvent() {
        setTopLeftButton(R.mipmap.back_img, v -> {
            if (questionType == 1){
                hint(1);
            }else{
                finish();
            }
        });
        if (questionType == 1) {
            setTopRightButton("", R.mipmap.to_result_img, v -> {
                hint(2);
            });
        }
        setTopTitleClick(v -> {
            if (!FirstClickUtils.isClickSoFast(350)) {
                if (window.isShowing()) {
                    return;
                }
                window.showAtLocation();
                List<SelectSubjectBean> subjectBeanList = new ArrayList<>();
                for (int i = 0; i < fragmentList.size(); i++) {
                    int currentSubject = i + 1;
                    BaseFragment fragment = fragmentList.get(i);
                    SelectSubjectBean bean = new SelectSubjectBean();
                    if (fragment instanceof RadioFragment) {
                        bean.setSelectStatus(((RadioFragment) fragment).getSubjectSelectStatus());
                        bean.setSubject("" + currentSubject);
                    } else if (fragment instanceof JudgeFragment) {
                        bean.setSelectStatus(((JudgeFragment) fragment).getSubjectSelectStatus());
                        bean.setSubject("" + currentSubject);
                    } else if (fragment instanceof MultiselectFragment) {
                        bean.setSelectStatus(((MultiselectFragment) fragment).getSubjectSelectStatus());
                        bean.setSubject("" + currentSubject);
                    }
                    subjectBeanList.add(bean);
                }

                window.setSelectSubjectData(SubjectActivity.this,
                        "" + successNum, "" + failNum, subjectBeanList);
                window.setSelectSubjectOnClick(position -> mBinding.viewpagerSubject.setCurrentItem(position));
            }
        });


        mBinding.viewpagerSubject.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.e("");
            }

            @Override
            public void onPageSelected(int position) {
                lastAnswer = position;
                if(position > 9){
                    if(!SysConfig.getConfig(mContext).getUserVip(SharedPreferenceUtils.getParams("examTypeId",mContext)).equals("99")){
                        StyledDialog.buildIosAlert("提示", "您当前不是Vip用户，暂时不能答后面的付费题目，是否付费开启Vip模式？", new MyDialogListener() {
                            @Override
                            public void onFirst() {
                                startActivity(PayActivity.class);
                                finish();
                            }

                            @Override
                            public void onSecond() {
                                finish();
                            }
                        }).show();
                    }else{
                        MyLog.i(">>>进入下一题:" + position);
                        selectIsCollections(position);
                        int newPostion = position + 1;
                        if (questionType == 1) {// 1 模拟考试 2 章节练习
                            if (type == 1){// 1 科目1 2 科目4
                                if(model4>0&&model5>0){
                                    setTitle(R.mipmap.subject_manager_img, newPostion + "/"+(model4+model5), R.mipmap.button_select_subject_img);
                                }else{
                                    setTitle(R.mipmap.subject_manager_img, newPostion + "/"+(model1+model2+model3+model6), R.mipmap.button_select_subject_img);
                                }

                            }
                        } else {
                            setTitle(R.mipmap.subject_manager_img, "顺序练习 " + newPostion + "/" + dataList.size()
                                    , R.mipmap.button_select_subject_img);
                        }
                    }
                }else{
                    MyLog.i(">>>进入下一题:" + position);
                    selectIsCollections(position);
                    int newPostion = position + 1;
                    if (questionType == 1) {// 1 模拟考试 2 章节练习
                        if (type == 1){// 1 科目1 2 科目4
                            if(model4>0&&model5>0){
                                setTitle(R.mipmap.subject_manager_img, newPostion + "/"+(model4+model5), R.mipmap.button_select_subject_img);
                            }else{
                                setTitle(R.mipmap.subject_manager_img, newPostion + "/"+(model1+model2+model3+model6), R.mipmap.button_select_subject_img);
                            }
                        }
                    } else {
                        setTitle(R.mipmap.subject_manager_img, "顺序练习 " + newPostion + "/" + dataList.size()
                                , R.mipmap.button_select_subject_img);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mBinding.chronometer.setOnChronometerTickListener(chronometer -> {
            long time1 = MyUtils.getInstance().string2Date("mm:ss", chronometer.getText().toString());
            LogUtil.e(time1+"");
            LogUtil.e(questionTime+"");
            if (time1 >= questionTime) {
                chronometer.stop();
                MyLog.i("时间到了");
                toResult();
            }
        });

        mBinding.lvnCollectionSubject.setOnClickListener(v -> {
            if (!FirstClickUtils.isClickSoFast(300)) {
                BaseFragment fragment = fragmentList.get(mBinding.viewpagerSubject.getCurrentItem());
                boolean isCollections;
                if (fragment instanceof RadioFragment) {//单选
                    isCollections = ((RadioFragment) fragment).isCollections();
                    saveCollections(fragment, isCollections, ((RadioFragment) fragment).getBean());
                } else if (fragment instanceof JudgeFragment) {//判断
                    isCollections = ((JudgeFragment) fragment).isCollections();
                    saveCollections(fragment, isCollections, ((JudgeFragment) fragment).getBean());
                } else if (fragment instanceof MultiselectFragment) {//多选
                    isCollections = ((MultiselectFragment) fragment).isCollections();
                    saveCollections(fragment, isCollections, ((MultiselectFragment) fragment).getBean());
                }
            }
        });

    }

    private void hint(int type) {
        int count;
        if (this.type == 1) {
            count = subject1Count - (successNum + failNum);
        } else {
            count = subject4Count - (successNum + failNum);
        }

        ResultDialog dialog = new ResultDialog(SubjectActivity.this, R.style.myDialogStyle);
        dialog.setContent(String.valueOf(count));
        if (type == 1){
            dialog.setLeftContent("确定离开");
            dialog.setLeftClickListener(this::finish);
        }
        dialog.show();
        dialog.setListener(this::toResult);
    }

    private void saveCollections(BaseFragment fragment, boolean isCollections, QuestionsBean bean) {
        if (!isCollections) {
            if (type == 1){
                sqlBrite.insertCollectionsSubject1(QuestionsBean.getContentValues(bean));
            }else if (type == 2){
                sqlBrite.insertCollectionsSubject4(QuestionsBean.getContentValues(bean));
            }

            if (fragment instanceof RadioFragment) {//单选
                ((RadioFragment) fragment).setCollections(true);
                handler.sendEmptyMessage(0x125);
            } else if (fragment instanceof JudgeFragment) {//判断
                ((JudgeFragment) fragment).setCollections(true);
                handler.sendEmptyMessage(0x125);
            } else if (fragment instanceof MultiselectFragment) {//多选
                ((MultiselectFragment) fragment).setCollections(true);
                handler.sendEmptyMessage(0x125);
            }
        } else {
            if (type == 1){
                sqlBrite.deleteCollectionsSubject1(" id = ? ", bean.getId());
            }else if (type == 2){
                sqlBrite.deleteCollectionsSubject4(" id = ? ", bean.getId());
            }

            if (fragment instanceof RadioFragment) {//单选
                ((RadioFragment) fragment).setCollections(false);
                handler.sendEmptyMessage(0x124);
            } else if (fragment instanceof JudgeFragment) {//判断
                ((JudgeFragment) fragment).setCollections(false);
                handler.sendEmptyMessage(0x124);
            } else if (fragment instanceof MultiselectFragment) {//多选
                ((MultiselectFragment) fragment).setCollections(false);
                handler.sendEmptyMessage(0x124);
            }
        }
    }

    private void insertError(QuestionsBean bean, String myAnswer) {
        MyLog.i("插入错题>>>>" + bean.getExplains());
        String timeDate = MyUtils.getInstance().date2String("yyyy/MM/dd HH:mm:ss", System.currentTimeMillis());
        if (type == 1){
            sqlBrite.insertErrorSubject1(QuestionsBean.getContentValues(bean, timeDate, myAnswer,SharedPreferenceUtils.getParams("examTypeId",mContext),categoryId));
        }else if (type == 2){
            sqlBrite.insertErrorSubject4(QuestionsBean.getContentValues(bean, timeDate, myAnswer,SharedPreferenceUtils.getParams("examTypeId",mContext),categoryId));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBinding.chronometer.stop();

        SysConfig.getConfig(mContext).setCustomConfigInt("laster"+categoryId+"00000",lastAnswer);


        /*if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("1")){
            SysConfig.getConfig(mContext).setLastAnswer(lastAnswer);
        }

        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("2")){
            SysConfig.getConfig(mContext).setLastAnswer2(lastAnswer);
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("3")){
            SysConfig.getConfig(mContext).setLastAnswer3(lastAnswer);
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("4")){
            SysConfig.getConfig(mContext).setLastAnswer4(lastAnswer);
        }
        if(SharedPreferenceUtils.getParams("examTypeId",mContext).equals("5")){
            SysConfig.getConfig(mContext).setLastAnswer5(lastAnswer);
        }*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        long time1 = MyUtils.getInstance().string2Date("mm:ss", mBinding.chronometer.getText().toString());
        if (time1 >= questionTime) {
            mBinding.chronometer.stop();
            MyLog.i("时间到了");
            toResult();
        } else {
            mBinding.chronometer.start();
        }
    }

    //是否完成答题
    private void isSelectSubjectOk() {
        if (questionType == 1) {
            noWriteNum --;
            String time = "";
            double source = 0;
            boolean isOk = false;
            if(model4>0&&model5>0){
                if (subject1Count == (successNum + failNum)) {
                    mBinding.chronometer.stop();
                    time = mBinding.chronometer.getText().toString();
                    source = (subject1Count - failNum) * 2;
                    isOk = true;
                }
            }else{
                if (type == 1) {
                    if (subject1Count == (successNum + failNum)) {
                        mBinding.chronometer.stop();
                        time = mBinding.chronometer.getText().toString();
                        source = successNum1+successNum+(successNum3*2);
                        isOk = true;
                    }
                } else if (type == 2) {
                    if (subject4Count == (successNum + failNum)) {
                        mBinding.chronometer.stop();
                        time = mBinding.chronometer.getText().toString();
                        source = subject4Count - (failNum * 2);
                        isOk = true;
                    }
                }
            }

            if (isOk && StringUtil.isNotEmpty(time)) {
                toResult();
            }

        }
    }

    private void toResult() {
        String time = "";
        double source = 0;
        time = mBinding.chronometer.getText().toString();
        if (type == 1) {
            source = successNum1 + successNum2*2 + successNum3;
        } else if (type == 2) {
            source = subject4Count - ((failNum * 2)+ noWriteNum );
        }
        Bundle bundle = new Bundle();
        bundle.putInt("subjectType", type);
        bundle.putString("time1", time);
        bundle.putDouble("source", source);
        bundle.putInt("questionType", 1);// 1 模拟考试 2 章节练习
        bundle.putInt("model1",model1);
        bundle.putInt("model2",model2);
        bundle.putInt("model3",model3);
        bundle.putInt("model4",model4);
        bundle.putInt("model5",model5);
        bundle.putInt("model6",model6);
        bundle.putInt("time",getIntent().getExtras().getInt("time"));
        bundle.putInt("manfen",manfen);
        bundle.putInt("type", type);
        bundle.putString("mId", categoryId);
        startActivity(bundle, ResultActivity.class);
        finish();
    }

    public ViewPager getViewPager() {
        if (mBinding.viewpagerSubject != null) {
            return mBinding.viewpagerSubject;
        }
        return null;
    }

    public int getQuestionType() {
        return questionType;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public int getFailNum() {
        return failNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }


    public double getSuccessNum1() {
        return successNum1;
    }

    public void setSuccessNum1(double successNum1) {
        this.successNum1 = successNum1;
    }

    public double getSuccessNum2() {
        return successNum2;
    }

    public void setSuccessNum2(double successNum2) {
        this.successNum2 = successNum2;
    }

    public double getSuccessNum3() {
        return successNum3;
    }

    public void setSuccessNum3(double successNum3) {
        this.successNum3 = successNum3;
    }


    @Override
    public void onRadioSelectSubjectSuccess(int success) {
        mBinding.tvYesSubject.setText("" + success);
        isSelectSubjectOk();
    }

    @Override
    public void onRadioSelectSubjectFail(int fail, String myAnswer) {
        mBinding.tvNoSubject.setText("" + fail);
        insertError(dataList.get(mBinding.viewpagerSubject.getCurrentItem()), myAnswer);
        isSelectSubjectOk();
    }

    @Override
    public void onJudgeSelectSubjectSuccess(int success) {
        mBinding.tvYesSubject.setText("" + success);
        isSelectSubjectOk();
    }

    @Override
    public void onJudgeSelectSubjectFail(int fail, String myAnswer) {
        mBinding.tvNoSubject.setText("" + fail);
        insertError(dataList.get(mBinding.viewpagerSubject.getCurrentItem()), myAnswer);
        isSelectSubjectOk();
    }

    @Override
    public void onMultSelectSubjectSuccess(int success) {
        mBinding.tvYesSubject.setText("" + success);
        isSelectSubjectOk();
    }

    @Override
    public void onMultSelectSubjectFail(int fail, String myAnswer) {
        mBinding.tvNoSubject.setText("" + fail);
        insertError(dataList.get(mBinding.viewpagerSubject.getCurrentItem()), myAnswer);
        isSelectSubjectOk();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (questionType ==1){
                hint(1);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
