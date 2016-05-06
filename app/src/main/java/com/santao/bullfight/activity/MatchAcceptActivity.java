package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.MyTeamAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchAcceptActivity extends BaseAppCompatActivity {


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;
    private String uid=null;

    private MyTeamAdapter adpater;

    private MatchFight entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_accept);

        ButterKnife.bind(this);

        initTopBar();

//        Bundle bundle = getIntent().getExtras();
//        if(bundle!=null){
//            uid = bundle.getString("id");
//        }

        Bundle bundle=getIntent().getExtras();
        entity = (MatchFight)bundle.getSerializable("matchfight");

        uid = baseApplication.getLoginUser().getId().toString();


        final GridLayoutManager layoutManager = new GridLayoutManager(MatchAcceptActivity.this,3);

        adpater = new MyTeamAdapter(MatchAcceptActivity.this);
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adapter.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adpater.getItemCount()) {
                    getData();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d("test", "onScrolled");

                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                //Log.d("","lastVisibleItem"+lastVisibleItem);
            }
        });

        adpater.setOnItemClickListener(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, Object id) {



                //不用付款，直接应战
                if(entity.getFee()==0)
                {
                    Team team  = (Team)id;
                    acceptGuest(team);
                }else
                {

                }


//                Team team  = (Team)id;
//
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("team",team);
//
//                Intent intent = new Intent(MatchAcceptActivity.this,TeamDetailActivity.class);
//                intent.putExtras(bundle);
//
//                startActivity(intent);



//                TeamEvent event = new TeamEvent();
//                event.setData(tag);
//                EventBus.getDefault().post(event);

            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(MatchAcceptActivity.this, R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adpater = new MyTeamAdapter(getActivity());
//                recyclerView.setAdapter(adpater);
                adpater.clear();
                getData();

            }
        });

        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        getData();

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("应战队伍");
    }

    private void getData()
    {
        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("teamuser/json/mymanateam?uid=" + uid);
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
                    adpater.addArrayList(list);
                    isLoadingMore = false;
                    page = page + 1;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        BaseApplication.getHttpQueue().add(stringRequest);

    }


    //客队应战成功
    private void acceptGuest(Team team)
    {
        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("matchfight/json/accept?uid=" + uid+"&tid="+team.getId()+"&mfid="+entity.getId());
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
                        Toast.makeText(MatchAcceptActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                        finish();
                    }else
                    {

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
