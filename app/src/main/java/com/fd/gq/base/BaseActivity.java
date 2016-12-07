package com.fd.gq.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fd.gq.util.ToastUtil;


/**
 * Created by admin on 2016/11/15.
 */
public class BaseActivity extends AppCompatActivity {

    public  Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void toast(String msg){
        ToastUtil.showMessage(mContext,msg);
    }

    public void toast(String msg, int gravity,int xOffset, int yOffset) {
        ToastUtil.showMessage(mContext, msg, gravity, xOffset, yOffset);
    }

}
