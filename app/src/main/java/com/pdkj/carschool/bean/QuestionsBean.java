package com.pdkj.carschool.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.pdkj.carschool.db.QuestionsMetaData;

import java.io.Serializable;

public class QuestionsBean implements Serializable {


    private String id;
    private String answer;//答案
    private String explains;//题目
    private String item1;//答案 1
    private String item2;//答案 2
    private String item3;//答案 3
    private String item4;//答案 4
    private String question;//问题
    private String type;// 1 单选 2 判断 3 多选
    private String url;
    private String myAnswer;
    private String categoryId;
    private String examTypeId;
    private String time;
    private String categoryType;
    private String isScene;

    @Override
    public String toString() {
        return "QuestionsBean{" +
                "id='" + id + '\'' +
                ", answer='" + answer + '\'' +
                ", explains='" + explains + '\'' +
                ", item1='" + item1 + '\'' +
                ", item2='" + item2 + '\'' +
                ", item3='" + item3 + '\'' +
                ", item4='" + item4 + '\'' +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", myAnswer='" + myAnswer + '\'' +
                ", time='" + time + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", examTypeId='" + examTypeId + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", isScene='" + isScene + '\'' +
                '}';
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getExamTypeId() {
        return examTypeId;
    }

    public void setExamTypeId(String examTypeId) {
        this.examTypeId = examTypeId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getIsScene() {
        return isScene;
    }

    public void setIsScene(String isScene) {
        this.isScene = isScene;
    }

    public QuestionsBean() {
    }

    public static ContentValues getContentValues(QuestionsBean bean){
        ContentValues values = new ContentValues();
        values.put(QuestionsMetaData.MetaData.ID,bean.getId());
        values.put(QuestionsMetaData.MetaData.ANSWER,bean.getAnswer());
        values.put(QuestionsMetaData.MetaData.EXPLAINS,bean.getExplains());
        values.put(QuestionsMetaData.MetaData.ITEM1,bean.getItem1());
        values.put(QuestionsMetaData.MetaData.ITEM2,bean.getItem2());
        values.put(QuestionsMetaData.MetaData.ITEM3,bean.getItem3());
        values.put(QuestionsMetaData.MetaData.ITEM4,bean.getItem4());
        values.put(QuestionsMetaData.MetaData.QUESTION,bean.getQuestion());
        values.put(QuestionsMetaData.MetaData.TYPE,bean.getType());
        values.put(QuestionsMetaData.MetaData.CATEGORYID,bean.getCategoryId());
        values.put(QuestionsMetaData.MetaData.URL,bean.getUrl());
        values.put(QuestionsMetaData.MetaData.EXAMTYPEID,bean.getExamTypeId());
        values.put(QuestionsMetaData.MetaData.CATEGORYTYPE,bean.getCategoryType());
        values.put(QuestionsMetaData.MetaData.ISSCENE,bean.getIsScene());
        return values;
    }

    public static ContentValues getContentValues(QuestionsBean bean,String time,String myAnswer,String examTypeId,String categoryId){
        ContentValues values = new ContentValues();
        values.put(QuestionsMetaData.MetaData.ID,bean.getId());
        values.put(QuestionsMetaData.MetaData.ANSWER,bean.getAnswer());
        values.put(QuestionsMetaData.MetaData.EXPLAINS,bean.getExplains());
        values.put(QuestionsMetaData.MetaData.ITEM1,bean.getItem1());
        values.put(QuestionsMetaData.MetaData.ITEM2,bean.getItem2());
        values.put(QuestionsMetaData.MetaData.ITEM3,bean.getItem3());
        values.put(QuestionsMetaData.MetaData.ITEM4,bean.getItem4());
        values.put(QuestionsMetaData.MetaData.QUESTION,bean.getQuestion());
        values.put(QuestionsMetaData.MetaData.TYPE,bean.getType());
        values.put(QuestionsMetaData.MetaData.TIME,time);
        values.put(QuestionsMetaData.MetaData.MYANSWER,myAnswer);
        values.put(QuestionsMetaData.MetaData.CATEGORYID,categoryId);
        values.put(QuestionsMetaData.MetaData.URL,bean.getUrl());
        values.put(QuestionsMetaData.MetaData.EXAMTYPEID,examTypeId);
        values.put(QuestionsMetaData.MetaData.CATEGORYTYPE,bean.getCategoryType());
        values.put(QuestionsMetaData.MetaData.ISSCENE,bean.getIsScene());
        return values;
    }

    public static QuestionsBean getQuestionsBean(Cursor cursor){
        int idColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.ID);
        int answerColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.ANSWER);
        int explainsColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.EXPLAINS);
        int item1Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM1);
        int item2Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM2);
        int item3Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM3);
        int item4Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM4);
        int questionColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.QUESTION);
        int urlColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.URL);
        int typeColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.TYPE);
        int categoryId = cursor.getColumnIndex(QuestionsMetaData.MetaData.CATEGORYID);
        int examTypeId = cursor.getColumnIndex(QuestionsMetaData.MetaData.EXAMTYPEID);
        int categoryType = cursor.getColumnIndex(QuestionsMetaData.MetaData.CATEGORYTYPE);
        int isScene = cursor.getColumnIndex(QuestionsMetaData.MetaData.ISSCENE);
        QuestionsBean bean = new QuestionsBean();
        bean.setId(cursor.getString(idColumn));
        bean.setAnswer(cursor.getString(answerColumn));
        bean.setExplains(cursor.getString(explainsColumn));
        bean.setItem1(cursor.getString(item1Column));
        bean.setItem2(cursor.getString(item2Column));
        bean.setItem3(cursor.getString(item3Column));
        bean.setItem4(cursor.getString(item4Column));
        bean.setQuestion(cursor.getString(questionColumn));
        bean.setType(cursor.getString(typeColumn));
        bean.setUrl(cursor.getString(urlColumn));
        bean.setCategoryId(cursor.getString(categoryId));
        bean.setExamTypeId(cursor.getString(examTypeId));
        bean.setCategoryType(cursor.getString(categoryType));
        bean.setIsScene(cursor.getString(isScene));
//        MyLog.i("数据>>>>"+bean.toString());
        return bean;
    }

    public static QuestionsBean getErrorQuestionsBean(Cursor cursor){
        int idColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.ID);
        int answerColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.ANSWER);
        int explainsColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.EXPLAINS);
        int item1Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM1);
        int item2Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM2);
        int item3Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM3);
        int item4Column = cursor.getColumnIndex(QuestionsMetaData.MetaData.ITEM4);
        int questionColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.QUESTION);
        int urlColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.URL);
        int myAnswerColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.MYANSWER);
        int typeColumn = cursor.getColumnIndex(QuestionsMetaData.MetaData.TYPE);
        int categoryId = cursor.getColumnIndex(QuestionsMetaData.MetaData.CATEGORYID);
        int examTypeId = cursor.getColumnIndex(QuestionsMetaData.MetaData.EXAMTYPEID);
        int categoryType = cursor.getColumnIndex(QuestionsMetaData.MetaData.CATEGORYTYPE);
        int isScene = cursor.getColumnIndex(QuestionsMetaData.MetaData.ISSCENE);
        QuestionsBean bean = new QuestionsBean();
        bean.setId(cursor.getString(idColumn));
        bean.setAnswer(cursor.getString(answerColumn));
        bean.setExplains(cursor.getString(explainsColumn));
        bean.setItem1(cursor.getString(item1Column));
        bean.setItem2(cursor.getString(item2Column));
        bean.setItem3(cursor.getString(item3Column));
        bean.setItem4(cursor.getString(item4Column));
        bean.setQuestion(cursor.getString(questionColumn));
        bean.setType(cursor.getString(typeColumn));
        bean.setUrl(cursor.getString(urlColumn));
        bean.setCategoryId(cursor.getString(categoryId));
        bean.setMyAnswer(cursor.getString(myAnswerColumn));
        bean.setExamTypeId(cursor.getString(examTypeId));
        bean.setCategoryType(cursor.getString(categoryType));
        bean.setIsScene(cursor.getString(isScene));
        return bean;
    }
}
