package com.santao.bullfight.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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
import com.santao.bullfight.activity.MatchDetailActivity;
import com.santao.bullfight.activity.MatchDetailFinishActivity;
import com.santao.bullfight.activity.TeamDetailActivity;
import com.santao.bullfight.adapter.MatchTeamListAdpater;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.MatchFightEvent;
import com.santao.bullfight.event.MemberEvent;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import de.greenrobot.event.EventBus;


public class MatchTeamFragment extends BaseFragment {


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;


    private MatchTeamListAdpater adapter;



    private int status = -1;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_match_team;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        adapter = new MatchTeamListAdpater(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adpater.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
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



        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, Object id) {

                MatchFight entity = (MatchFight) id;

                //未接招
                if(entity.getStatus()==0)
                {
                    Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
                    //intent.putExtra("id", id.toString());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("matchfight",entity);

                    intent.putExtras(bundle);

                    //Log.d("","id:"+id);
                    //leagueListAdapter.getArrayList().get(id);
                    startActivity(intent);
                }


                //未开始
                if(entity.getStatus()==1)
                {
                    Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
                    //intent.putExtra("id", id.toString());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("matchfight",entity);

                    intent.putExtras(bundle);

                    //Log.d("","id:"+id);
                    //leagueListAdapter.getArrayList().get(id);
                    startActivity(intent);
                }


                //已结束
                if(entity.getStatus()==2)
                {
                    Intent intent = new Intent(getActivity(), MatchDetailFinishActivity.class);
                    //intent.putExtra("id", id.toString());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("matchfight",entity);

                    intent.putExtras(bundle);

                    //Log.d("","id:"+id);
                    //leagueListAdapter.getArrayList().get(id);
                    startActivity(intent);
                }


            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adapter = new MatchTeamListAdpater(getActivity());
//                recyclerView.setAdapter(adapter);
                adapter.clear();
                getData();

            }
        });

        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        getData();
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    private void getData()
    {
        String s = "";
        if(status!=-1)
        {
            s = "&status="+status;
        }

        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("matchfight/json/matchlist?matchType=1"+s+"&p=" + page);
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
                    adapter.addArrayList(list);
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


    public void onEventMainThread(TeamEvent event) {

        if(event.getEventName().equals(TeamEvent.TEAM_DETAIL))
        {
            Team team = (Team)event.getData();

            if(team==null)
            {
                return;
            }
            Intent intent = new Intent(getActivity(), TeamDetailActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("team",team);
            intent.putExtras(bundle);

            startActivity(intent);
        }

    }

    public void onEventMainThread(MatchFightEvent event) {

        if(event.getEventName().equals(MatchFightEvent.MATCHE_FILTER))
        {
            int idx = Integer.parseInt(event.getData().toString());

            if (idx==0)
            {
                status = -1;
            }

            if (idx==1)
            {
                status = 0;
            }

            if (idx==2)
            {
                status = 1;
            }

            if (idx==3)
            {
                status = 2;
            }

            page = 1;
            adapter = new MatchTeamListAdpater(getActivity());
            recyclerView.setAdapter(adapter);
            getData();

        }

    }


}
