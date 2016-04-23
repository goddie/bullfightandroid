package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.League;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LeagueJoinActivity extends BaseAppCompatActivity {


    private User user;

    @Bind(R.id.imgTeam)
    ImageView imgTeam;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.txt4)
    TextView txt4;

    private League league;
    private String leagueid;
    private String teamid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_join);

        ButterKnife.bind(this);

        initTopBar();

        EventBus.getDefault().register(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("league")) {
            league = (League)bundle.getSerializable("league");
            leagueid = league.getId().toString();
        }

        txt4.setText( String.valueOf(league.getFee()));

        //user = baseApplication.getLoginUser();
    }


    @OnClick({R.id.imgTeam})
    public void selTeam(View v)
    {
        Intent intent = new Intent(LeagueJoinActivity.this, CreateMatchTeamActivity.class);

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

        teamid = entity.getId().toString();

        Picasso.with(getApplicationContext()).load(HttpUtil.BASE_URL + entity.getAvatar()).placeholder(R.mipmap.holder)
                .into(imgTeam);

        //Log.d("harvic", msg);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus)
        {
            user = baseApplication.getLoginUser();
        }

    }


    @OnClick({R.id.imgNext})
    public void joinLeague()
    {
        if(HttpUtil.isNullOrEmpty(leagueid)||HttpUtil.isNullOrEmpty(teamid))
        {
            return;
        }

        String contact = txt2.getText().toString();
        String phone = txt3.getText().toString();
        String pay =  txt4.getText().toString();

        String url = HttpUtil.getAbsoluteUrl("leagueteam/json/add?leagueid=" + leagueid + "&teamid=" + teamid + "&contact=" + contact + "&phone=" + phone + "&pay=" + pay);

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
                        Toast.makeText(LeagueJoinActivity.this, getString(R.string.join_success), Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(LeagueJoinActivity.this, getString(R.string.join_fail), Toast.LENGTH_SHORT).show();
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
