package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends BaseAppCompatActivity {


    @Bind({R.id.txt1})
    TextView txt1;

    @Bind({R.id.txt2})
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ButterKnife.bind(this);
        initTopBar();

        User user = baseApplication.getLoginUser();

        txt1.setText(user.getUsername());
        txt2.setText(user.getPhone());
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("账户设置");
    }

    @OnClick({R.id.btn3})
    public void btn3Click()
    {
        Intent intent = new Intent(AccountActivity.this,PasswordOneActivity.class);
        startActivity(intent);
    }



}
