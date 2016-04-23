package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateMatchFourActivity extends BaseAppCompatActivity {


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


    private MatchFight matchFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_four);

        ButterKnife.bind(this);

        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }

    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("创建比赛");
    }


    @OnClick({R.id.img11,R.id.img12,R.id.img13})
    public void judgeClick(View view)
    {
        img11.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img12.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img13.setBackgroundResource(R.mipmap.shared_selector_inactive);

        Button imageView = (Button)view;
        imageView.setBackgroundResource(R.mipmap.shared_selector_active);

        if(view.getId()==R.id.img1)
        {
            matchFight.setJudge(1);
        }


        if(view.getId()==R.id.img2)
        {
            matchFight.setJudge(2);
        }


        if(view.getId()==R.id.img3)
        {
            matchFight.setJudge(3);
        }

    }


    @OnClick({R.id.img21,R.id.img22,R.id.img23})
    public void dataClick(View view)
    {
        img21.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img22.setBackgroundResource(R.mipmap.shared_selector_inactive);
        img23.setBackgroundResource(R.mipmap.shared_selector_inactive);


        Button imageView = (Button)view;
        imageView.setBackgroundResource(R.mipmap.shared_selector_active);


        if(view.getId()==R.id.img21)
        {
            matchFight.setDataRecord(1);
        }


        if(view.getId()==R.id.img22)
        {
            matchFight.setDataRecord(2);
        }


        if(view.getId()==R.id.img23)
        {
            matchFight.setDataRecord(3);
        }

    }

    @OnClick({R.id.imgNext})
    public void nextClick()
    {
        Intent intent = new Intent(CreateMatchFourActivity.this, CreateMatchFiveActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("matchFight", matchFight);

        intent.putExtras(bundle);


        startActivity(intent);
    }



    @OnClick({R.id.imgFree})
    public void freeClick()
    {


        Intent intent = new Intent(CreateMatchFourActivity.this, CreateMatchFiveActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("matchFight", matchFight);

        intent.putExtras(bundle);


        startActivity(intent);

    }
}
