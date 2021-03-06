package com.santao.bullfight.fragment;


import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.activity.MatchDetailActivity;
import com.santao.bullfight.adapter.MatchInfoTeamAdapter;
import com.santao.bullfight.adapter.MatchTeamListAdpater;
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
 * 队伍信息
 */
public class MatchInfoTeamFragment extends BaseFragment {



    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;
    private String leagueid=null;

    private MatchInfoTeamAdapter adpater;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_match_team;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle!=null){
            leagueid = bundle.getString("id");
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        adpater = new MatchInfoTeamAdapter(getActivity());
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(layoutManager);



        getData();
    }


    private void getData()
    {

        Intent intent= getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        MatchFight entity = (MatchFight)bundle.getSerializable("matchfight");

        ArrayList<Object> list = new ArrayList<Object>();

        DecimalFormat df = new DecimalFormat("0.0");


        Team host = entity.getHost();
        Team guest = entity.getGuest();


        if(host==null && guest==null)
        {
            return;
        }

        ArrayList<String> li =new ArrayList<String>();
        li.add((int)host.getPlayCount()+" 战 "+(int)host.getWin()+" 胜 ");
        li.add("历史战绩");
        if(guest==null)
        {
            li.add("");
        }else {
            li.add((int)guest.getPlayCount()+" 战 "+(int)guest.getWin()+" 胜 ");
        }
        list.add(li);


        li =new ArrayList<String>();
        li.add(String.valueOf(df.format(host.getScoring())));
        li.add("场均得分");
        if(guest==null)
        {
            li.add("");
        }else {
            li.add(String.valueOf(df.format(guest.getScoring())));
        }
        list.add(li);


        li =new ArrayList<String>();
        li.add(String.valueOf(df.format(host.getRebound())));
        li.add("场均篮板");
        if(guest==null)
        {
            li.add("");
        }else {
            li.add(String.valueOf(df.format(guest.getRebound())));
        }
        list.add(li);

        li =new ArrayList<String>();
        li.add(String.valueOf(df.format(host.getAssist())));
        li.add("场均助攻");
        if(guest==null)
        {
            li.add("");
        }else {
            li.add(String.valueOf(df.format(guest.getAssist())));
        }
        list.add(li);


        li =new ArrayList<String>();
        li.add(String.valueOf(df.format(host.getTurnover())));
        li.add("场均失误");
        if(guest==null)
        {
            li.add("");
        }else {
            li.add(String.valueOf(df.format(guest.getTurnover())));
        }
        list.add(li);


        li =new ArrayList<String>();
        li.add(String.valueOf(df.format(host.getBlock())));
        li.add("场均盖帽");
        if(guest==null)
        {
            li.add("");
        }else {
            li.add(String.valueOf(df.format(guest.getBlock())));
        }
        list.add(li);

        adpater.addArrayList(list);
    }
}
