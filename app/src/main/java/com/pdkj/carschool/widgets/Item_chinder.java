package com.pdkj.carschool.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pdkj.carschool.R;
import com.pdkj.carschool.activity.MyCollectionsActivity;
import com.pdkj.carschool.activity.StartExamActivity;
import com.pdkj.carschool.activity.SubjectActivity;
import com.pdkj.carschool.utils.SysConfig;

public class Item_chinder extends LinearLayout {


    private RelativeLayout rl_item;
    private TextView tv_name;
    private String name;
    private Context mContext;
    private int mClickType;
    private int mQuestionType;
    private String mId;

    public Item_chinder(Context context) {
        super(context);
    }


    public Item_chinder(Context context,String text,String id,int clickType) {
        this(context);
        mContext = context;
        name = text;
        mClickType = clickType;
        mId = id;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_childer, this,true);
        rl_item = findViewById(R.id.rl_item);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);

        rl_item.setOnClickListener(v -> {
            switch (mClickType){
                case 1:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("questionType",2);
                    bundle2.putInt("type",1);
                    bundle2.putString("mId",mId);
                    bundle2.putString("name",name);
                    Intent intent = new Intent(mContext, SubjectActivity.class);
                    intent.putExtras(bundle2);
                    mContext.startActivity(intent);
                    break;
                case 2:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("type",1);
                    bundle1.putString("mId",mId);
                    bundle1.putString("name",name);
                    SysConfig.getConfig(mContext).setCustomConfig("nameones",name);
                    Intent intent1 = new Intent(mContext, StartExamActivity.class);
                    intent1.putExtras(bundle1);
                    mContext.startActivity(intent1);
                    break;
                case 3:
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("type",1);
                    Intent intent2 = new Intent(mContext, MyCollectionsActivity.class);
                    intent2.putExtras(bundle3);
                    mContext.startActivity(intent2);
                    break;
            }
        });
    }
}
