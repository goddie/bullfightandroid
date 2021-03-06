package com.santao.bullfight.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.activity.MatchDetailActivity;
import com.santao.bullfight.adapter.MatchTeamListAdpater;
import com.santao.bullfight.adapter.TeamRecordAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 球队战绩
 */
public class TeamRecordFragment extends BaseFragment {

    private Team team;

    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;



    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.container)
    LinearLayout container;

    private  int page=1;
    private boolean isLoadingMore = false;


    private TeamRecordAdapter adpater;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_team_record;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Intent intent= getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        team = (Team)bundle.getSerializable("team");

        DecimalFormat df = new DecimalFormat("0");


        txt1.setText(df.format(team.getPlayCount()));

        txt2.setText(df.format(team.getWin()));

        txt3.setText(df.format(team.getLose()));


//        LinearLayout ll = (LinearLayout)mRootView.findViewById(R.id.container);
//
//        ll.setBackgroundColor(getResources().getColor(R.color.colorAppBgLight));

//        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)ll.getLayoutParams(); //取控件textView当前的布局参数
//        //linearParams = 20;// 控件的高强制设成20
//
//        ll.setLayoutParams(linearParams); //使设置好的布局参数应用到控件



        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        adpater = new TeamRecordAdapter(getActivity());
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adpater.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adpater.getItemCount()) {
                    //getData();
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




                MatchFight entity = (MatchFight)id;

                Intent intent = new Intent(getActivity(), MatchDetailActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("matchfight",entity);

                intent.putExtras(mBundle);

                //Log.d("","id:"+id);
                //leagueListAdapter.getArrayList().get(id);
                startActivity(intent);
            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adpater = new TeamRecordAdapter(getActivity());
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

    private void getData()
    {

        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("matchdatateam/json/teammatch?tid="+team.getId().toString() +"&page="+ page);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MatchFight entity = gson.fromJson(jsonArray.get(i).toString(), MatchFight.class);
                        list.add(entity);
                    }
                    adpater.addArrayList(list);
                    isLoadingMore = false;
                    page = page + 1;

                    //resize();


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

    void resize()
    {
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) container.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = adpater.getArrayList().size() * 200;
        container.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }


}


