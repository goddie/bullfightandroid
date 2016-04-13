package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.santao.bullfight.R;
import com.santao.bullfight.core.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        ButterKnife.bind(this);

        initTopBar();

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("设置");
    }


    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4})
    public  void click(View view)
    {
        if(view.getId()==R.id.btn1)
        {
            Intent intent = new Intent(ConfigActivity.this,AccountActivity.class);
            startActivity(intent);
        }


        if(view.getId()==R.id.btn2)
        {
            Intent intent = new Intent(ConfigActivity.this,AboutActivity.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.btn3)
        {
            Intent intent = new Intent(ConfigActivity.this,FeedbackActivity.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.btn4)
        {

            baseApplication.setLoginUser(null);
            Utils.clearLocalUser(getApplicationContext());
            finish();

        }
    }
}
