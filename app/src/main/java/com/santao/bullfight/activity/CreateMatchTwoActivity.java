package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CreateMatchTwoActivity extends BaseAppCompatActivity {


    @Bind(R.id.imgTeam)
    ImageView imgTeam;

    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.img3)
    ImageView img3;

    @Bind(R.id.img4)
    ImageView img4;

    @Bind(R.id.img5)
    ImageView img5;


    private MatchFight matchFight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_two);


        ButterKnife.bind(this);

        initTopBar();

        EventBus.getDefault().register(this);

        baseApplication.getLoginUser();


        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }

        bindTeam();

    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("创建比赛");
    }


    @OnClick({R.id.imgTeam})
    public void selTeam(View v)
    {
        Intent intent = new Intent(CreateMatchTwoActivity.this, CreateMatchTeamActivity.class);

        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(TeamEvent event) {

        Team entity = (Team)event.getData();

        if(entity==null || entity.getAvatar()==null || entity.getAvatar().equals(""))
        {
            return;
        }

        Picasso.with(getApplicationContext()).load(HttpUtil.BASE_URL + entity.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                .into(imgTeam);

        matchFight.setHost(entity);

        //Log.d("harvic", msg);
    }



    @OnClick({R.id.img1,R.id.img3,R.id.img4,R.id.img5})
    public void imgCreateClick(View v)
    {

        img1.setImageResource(R.mipmap.shared_picker_unselected);
        img3.setImageResource(R.mipmap.shared_picker_unselected);
        img4.setImageResource(R.mipmap.shared_picker_unselected);
        img5.setImageResource(R.mipmap.shared_picker_unselected);

        ImageView imageView = (ImageView)v;
        imageView.setImageResource(R.mipmap.shared_picker_selected);

        if(v.getId() == R.id.img1)
        {
            matchFight.setTeamSize(1);
        }

        if(v.getId() == R.id.img2)
        {
            matchFight.setTeamSize(3);
        }

        if(v.getId() == R.id.img3)
        {
            matchFight.setTeamSize(4);
        }

        if(v.getId() == R.id.img4)
        {
            matchFight.setTeamSize(5);
        }

    }



    @OnClick({R.id.imgNext})
    public void goNext(View v)
    {
        Intent intent = new Intent(CreateMatchTwoActivity.this, CreateMatchThreeActivity.class);


        Bundle bundle = new Bundle();
        bundle.putSerializable("matchFight", matchFight);

        intent.putExtras(bundle);

        startActivity(intent);

        finish();
    }


    void bindTeam()
    {
        User user = baseApplication.getLoginUser();

        String url = HttpUtil.getAbsoluteUrl("teamuser/json/mymanateam?uid=" + user.getId());

        final Team[] team = {null};

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(jsonArray.length()>0)
                    {
                        team[0] = gson.fromJson(jsonArray.get(0).toString(), Team.class);

                        Picasso.with(getApplicationContext()).load(HttpUtil.BASE_URL + team[0].getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                                .into(imgTeam);
                        matchFight.setHost(team[0]);

                    }

//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        team[0] = gson.fromJson(jsonArray.get(i).toString(), Team.class);
//                        list.add(entity);
//                    }

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
