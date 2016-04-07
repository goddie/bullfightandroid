package com.santao.bullfight.fragment;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.santao.bullfight.adapter.MatchInfoAdapter;
import com.santao.bullfight.adapter.MatchTeamListAdpater;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 比赛信息
 */
public class MatchInfoFragment extends BaseFragment {


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    private String leagueid=null;

    private MatchInfoAdapter adpater;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_match_info;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle!=null){
            leagueid = bundle.getString("id");
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        adpater = new MatchInfoAdapter(getActivity());
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

        if(entity.getArena()!=null)
        {
            MatchInfoData matchInfoData = new MatchInfoData();
            matchInfoData.setImgId(R.mipmap.shared_icon_location);
            matchInfoData.setText(entity.getArena().getName());

            list.add(matchInfoData);
        }


        MatchInfoData matchInfoData = new MatchInfoData();
        matchInfoData.setImgId(R.mipmap.shared_icon_time);
        matchInfoData.setText(HttpUtil.getDate(entity.getStart()));

        list.add(matchInfoData);


        if(!HttpUtil.isNullOrEmpty(entity.getWeather()))
        {
            matchInfoData = new MatchInfoData();
            matchInfoData.setImgId(R.mipmap.shared_icon_weather);
            matchInfoData.setText(entity.getWeather());

            list.add(matchInfoData);
        }


        if(entity.getJudge()>0 && entity.getDataRecord()>0)
        {
            matchInfoData = new MatchInfoData();
            matchInfoData.setImgId(R.mipmap.shared_icon_jurge);
            matchInfoData.setText("裁判员"+entity.getJudge()+"名，数据员"+entity.getDataRecord()+"名");

            list.add(matchInfoData);
        }

        if(!HttpUtil.isNullOrEmpty(entity.getContent()))
        {
            matchInfoData = new MatchInfoData();
            matchInfoData.setImgId(R.mipmap.activities_icon_comment_active);
            matchInfoData.setText(entity.getContent());

            list.add(matchInfoData);
        }



        adpater.addArrayList(list);

    }





    //代码如下
    public Bitmap getRes(String name)
    {
        ApplicationInfo appInfo =getActivity().getApplicationInfo();
        int resID = getResources().getIdentifier(name, "drawable", appInfo.packageName);
        return BitmapFactory.decodeResource(getResources(), resID);
    }


    public class MatchInfoData
    {
        private  int imgId;
        private String text;

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
