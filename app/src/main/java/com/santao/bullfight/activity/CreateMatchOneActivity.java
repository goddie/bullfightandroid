package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.santao.bullfight.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateMatchOneActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_one);

        ButterKnife.bind(this);
    }


    @OnClick({R.id.imgClose})
    public void imgClick(View v)
    {
//        Intent intent = new Intent(MatchFightActivity.this, CreateMatchOneActivity.class);

//
//        startActivity(intent);
        finish();
    }


    @OnClick({R.id.img1,R.id.img2})
    public void imgCreateClick(View v)
    {


        if(v.getId()==R.id.img1)
        {
            Intent intent = new Intent(CreateMatchOneActivity.this, CreateMatchTwoActivity.class);
            startActivity(intent);
        }


    }
}
