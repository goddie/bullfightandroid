package com.santao.bullfight.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.fragment.LeagueFragment;
import com.santao.bullfight.fragment.MatchTeamFragment;
import com.santao.bullfight.fragment.MatchWildFragment;
import com.santao.bullfight.fragment.TopFragment;


public class BaseAppCompatActivity extends AppCompatActivity implements TopFragment.TopListener {

    protected BaseApplication baseApplication = BaseApplication.getBaseApplication();
    protected TopFragment topFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    protected  void initTopBar()
    {
        View v = findViewById(R.id.topBar);
        if(v==null)
        {
            Log.e(getString(R.string.app_name),"Has no topBar!");
            return;
        }

        topFragment = new TopFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.topBar, topFragment);
        transaction.commit();

    }


    protected  void setTitle(String title)
    {
        topFragment.setTitle(title);
    }

    protected  void setTxtRight(String txt)
    {
        topFragment.setTxtRight(txt);
    }


    protected void setImgRight(int id)
    {
        topFragment.setImgRight(id);
    }

    protected void setImgLeft(int id){ topFragment.setImgLeft(id);}

    protected void setImgLeft(Bitmap img) { topFragment.setImgLeft(img); }

    @Override
    public void onTopFinish() {

    }

    @Override
    public void onTopRightClick() {

    }

    @Override
    public void onTopLeftClick() {

    }



}
