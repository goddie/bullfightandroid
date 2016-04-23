package com.santao.bullfight.fragment;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santao.bullfight.R;
import com.santao.bullfight.adapter.MatchInfoAdapter;
import com.santao.bullfight.adapter.UserDetailDataAdapter;
import com.santao.bullfight.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailDataFragment extends BaseFragment {

    private User user;


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private UserDetailDataAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_user_detail_data;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Bundle bundle =getActivity().getIntent().getExtras();

        if (bundle != null && bundle.containsKey("user")) {
            user = (User)bundle.getSerializable("user");
        }

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2,1,false);

        adapter = new UserDetailDataAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        getData();
    }

    private void getData()
    {

        DecimalFormat  df =new DecimalFormat("0.0");

        ArrayList<Object> list = new ArrayList<Object>();


        ArrayList<Object> item = new ArrayList<Object>();
        item.add("三分球命中率");
        item.add(df.format(user.getThreeGoalPercent()*100)+"%");
        list.add(item);

        item = new ArrayList<Object>();
        item.add("投篮命中率");
        item.add(df.format(user.getGoalPercent()*100)+"%");
        list.add(item);

        item = new ArrayList<Object>();
        item.add("场均得分");
        item.add(df.format(user.getScoringAvg())+"");
        list.add(item);


        item = new ArrayList<Object>();
        item.add("场均犯规");
        item.add(df.format(user.getFoul())+"");
        list.add(item);


        item = new ArrayList<Object>();
        item.add("场均篮板");
        item.add(df.format(user.getRebound())+"");
        list.add(item);


        item = new ArrayList<Object>();
        item.add("场均助攻");
        item.add(df.format(user.getAssist())+"");
        list.add(item);


        item = new ArrayList<Object>();
        item.add("场均失误");
        item.add(df.format(user.getTurnover())+"");
        list.add(item);


        item = new ArrayList<Object>();
        item.add("场均抢断");
        item.add(df.format(user.getSteal())+"");
        list.add(item);



        adapter.addArrayList(list);

    }
}
