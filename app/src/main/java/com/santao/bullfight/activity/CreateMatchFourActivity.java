package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.santao.bullfight.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateMatchFourActivity extends AppCompatActivity {


    @Bind(R.id.img11)
    Button img11;

    @Bind(R.id.img12)
    Button img12;

    @Bind(R.id.img13)
    Button img13;

    @Bind(R.id.img21)
    Button img21;

    @Bind(R.id.img22)
    Button img22;

    @Bind(R.id.img23)
    Button img23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_four);

        ButterKnife.bind(this);


    }


    @OnClick({R.id.img11,R.id.img12,R.id.img13})
    public void judgeClick(View view)
    {
        img11.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img12.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img13.setBackgroundResource(R.mipmap.shared_selector_inactive);

        Button imageView = (Button)view;
        imageView.setBackgroundResource(R.mipmap.shared_selector_active);

    }


    @OnClick({R.id.img21,R.id.img22,R.id.img23})
    public void dataClick(View view)
    {
        img21.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img22.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img23.setBackgroundResource(R.mipmap.shared_selector_inactive);


        Button imageView = (Button)view;
        imageView.setBackgroundResource(R.mipmap.shared_selector_active);
    }

    @OnClick({R.id.imgNext})
    public void nextClick()
    {
        Intent intent = new Intent(CreateMatchFourActivity.this, CreateMatchFiveActivity.class);
        startActivity(intent);
    }
}
