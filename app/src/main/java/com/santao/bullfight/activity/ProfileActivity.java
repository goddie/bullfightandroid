package com.santao.bullfight.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends BaseAppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {


    @Bind(R.id.mainLayout)
    LinearLayout mainLayout;

    @Bind(R.id.txt4)
    TextView txt4;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4);

        //Log.d("","--------- ProfileActivity onCreate");

        ButterKnife.bind(this);

        initTopBar();



        //User user = baseApplication.getLoginUser();
        //txt4.setText(user.getCity());

        ViewTreeObserver vto = mainLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);


    }

    @Override
    public void onGlobalLayout() {

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("我");
        setImgRight(R.mipmap.nav_btn_settings);
    }


    @Override
    public void onTopRightClick() {
        super.onTopRightClick();

        Intent intent = new Intent(ProfileActivity.this,ConfigActivity.class);
        startActivity(intent);

    }

    @OnClick({R.id.btn1})
    public void edit()
    {
        Intent intent = new Intent(ProfileActivity.this,MyInfoActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.btn2})
    public void mydata()
    {
        Intent intent = new Intent(ProfileActivity.this,MyDataActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.btn3})
    public  void myteam()
    {
        Intent intent = new Intent(ProfileActivity.this,MyTeamActivity.class);
        startActivity(intent);
    }






}
