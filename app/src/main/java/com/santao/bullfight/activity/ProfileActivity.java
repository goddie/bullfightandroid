package com.santao.bullfight.activity;

import android.app.Application;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.santao.bullfight.R;
import com.santao.bullfight.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseAppCompatActivity {

    @Bind(R.id.mainLayout)
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4);

        //Log.d("","--------- ProfileActivity onCreate");

        ButterKnife.bind(this);

        //baseApplication.getLoginUser();

    }




}
