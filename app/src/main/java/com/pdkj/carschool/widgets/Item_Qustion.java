package com.pdkj.carschool.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.MyCollectionsActivity;
import com.pdkj.carschool.activity.StartExamActivity;
import com.pdkj.carschool.activity.SubjectActivity;

public class Item_Qustion extends LinearLayout {


    private TextView tv_name;
    private String name;
    private Context mContext;
    private ImageView im_right;
    private String scores;
    private TextView tv_time;

    public Item_Qustion(Context context) {
        super(context);
    }


    public Item_Qustion(Context context, String time,String score) {
        this(context);
        mContext = context;
        name = time;
        scores = score;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_question, this,true);
        tv_name = findViewById(R.id.tv_name);
        im_right = findViewById(R.id.im_right);
        tv_time = findViewById(R.id.tv_time);
        im_right.setVisibility(GONE);
        tv_name.setText(scores);
        tv_time.setText(name);
    }
}
