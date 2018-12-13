package com.pdkj.carschool.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;


@Table(name="question_record")
public class QuestionRecord {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "questionTime")
    private String questionTime;

    @Column(name = "score")
    private String score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
