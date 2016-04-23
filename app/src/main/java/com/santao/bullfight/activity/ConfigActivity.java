package com.santao.bullfight.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.santao.bullfight.R;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.event.MatchFightEvent;
import com.santao.bullfight.event.PageEvent;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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

            new android.support.v7.app.AlertDialog.Builder(ConfigActivity.this)
                    .setTitle("确认退出")
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            baseApplication.setLoginUser(null);
                            Utils.clearLocalUser(getApplicationContext());

                            PageEvent event = new PageEvent(PageEvent.PAGE_SWITCH);
                            event.setData(0);
                            EventBus.getDefault().post(event);

                            finish();

                        }
                    })
                    .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();



        }
    }
}
