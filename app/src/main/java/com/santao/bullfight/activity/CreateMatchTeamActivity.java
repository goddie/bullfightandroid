package com.santao.bullfight.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.TeamListAdapter;
import com.santao.bullfight.event.BaseEvent;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class CreateMatchTeamActivity extends BaseAppCompatActivity {


    @Bind(R.id.teamList)
    RecyclerView recyclerView;


    private TeamListAdapter listAdapter;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_team);


        ButterKnife.bind(this);

        initTopBar();

        user = baseApplication.getLoginUser();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        listAdapter = new TeamListAdapter(this);

        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {

                TeamEvent event = new TeamEvent(TeamEvent.TEAM_SELECTED);
                event.setData(tag);
                EventBus.getDefault().post(event);

                finish();

            }
        });


        getData();

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("我的球队");
    }

    private void getData() {

        String url = HttpUtil.getAbsoluteUrl("teamuser/json/mymanateam?uid=" + user.getId());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Team entity = gson.fromJson(jsonArray.get(i).toString(), Team.class);
                        list.add(entity);
                    }
                    listAdapter.addArrayList(list);


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
