package com.santao.bullfight.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CreateMatchFiveActivity extends BaseAppCompatActivity {

    @Bind(R.id.imgNext)
    Button imgNext;


    private MatchFight matchFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_five);

        ButterKnife.bind(this);

        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }

        if(matchFight.getFee()==0)
        {
            imgNext.setText("免费创建比赛");
        }
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("创建比赛");


    }


    @OnClick({R.id.imgNext})
    public void btnClick()
    {
        createMatch();
    }

    void createMatch()
    {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        User user = baseApplication.getLoginUser();


        String startStr = "";
        try {
            startStr = URLEncoder.encode(sdf.format(new Date(matchFight.getStart())),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String endStr = "";
        try {
            endStr = URLEncoder.encode(sdf.format(new Date(matchFight.getStart())),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb =new StringBuilder();
        sb.append("?uid="+user.getId().toString());
        sb.append("&tid="+ matchFight.getHost().getId().toString());
        sb.append("&aid="+matchFight.getArena().getId().toString());
        sb.append("&matchType="+matchFight.getMatchType());
        sb.append("&status=0");
        sb.append("&startStr="+ startStr);
        sb.append("&endStr="+ endStr);
        sb.append("&guestScore=0");
        sb.append("&hostScore=0");
        sb.append("&teamSize="+matchFight.getTeamSize());
        sb.append("&judge="+matchFight.getJudge());
        sb.append("&dataRecord="+matchFight.getDataRecord());
        sb.append("&isPay=1");
        sb.append("&fee=0");

        try {
            sb.append("&content="+ URLEncoder.encode(matchFight.getContent(), "UTF-8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        String url = HttpUtil.getAbsoluteUrl("matchfight/json/add"+sb.toString());

        final Team[] team = {null};

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = jsonObject.getInt("code");

                    if(code==1)
                    {
                        Toast.makeText(CreateMatchFiveActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                        finish();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        BaseApplication.getHttpQueue().add(stringRequest);
    }
}
