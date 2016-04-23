package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.santao.bullfight.R;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.User;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateMatchOneActivity extends BaseAppCompatActivity {


    private MatchFight matchFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_one);

        ButterKnife.bind(this);
        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }else
        {
            matchFight = new MatchFight();
        }

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("创建比赛");
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

            matchFight.setMatchType(1);

            Bundle bundle = new Bundle();
            bundle.putSerializable("matchFight", matchFight);

            intent.putExtras(bundle);

            startActivity(intent);

            finish();

        }


        if(v.getId()==R.id.img2)
        {
            Intent intent = new Intent(CreateMatchOneActivity.this, CreateMatchThreeActivity.class);
            matchFight.setMatchType(2);
            Bundle bundle = new Bundle();
            bundle.putSerializable("matchFight",matchFight);

            intent.putExtras(bundle);

            startActivity(intent);
        }

    }
}
