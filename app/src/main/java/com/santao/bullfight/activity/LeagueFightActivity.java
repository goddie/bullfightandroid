package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.LeagueFightAdapter;
import com.santao.bullfight.fragment.LeagueAssistFragment;
import com.santao.bullfight.fragment.LeagueFightFragment;
import com.santao.bullfight.fragment.LeagueFragment;
import com.santao.bullfight.fragment.LeagueReboundFragment;
import com.santao.bullfight.fragment.LeagueScoreFragment;
import com.santao.bullfight.fragment.LeagueTotalFragment;
import com.santao.bullfight.fragment.MatchTeamFragment;
import com.santao.bullfight.fragment.MatchWildFragment;
import com.santao.bullfight.model.League;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;
import com.santao.bullfight.widget.TabButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeagueFightActivity  extends BaseAppCompatActivity {


    @Bind(R.id.tab1)
    TabButton tab1;

    @Bind(R.id.tab2)
    TabButton tab2;

    @Bind(R.id.tab3)
    TabButton tab3;

    @Bind(R.id.tab4)
    TabButton tab4;

    @Bind(R.id.tab5)
    TabButton tab5;

    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;

    private FragmentManager fragmentManager;


    private League league;
    String leagueid = null;
    //String teamid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_fight);
        ButterKnife.bind(this);

        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("league")) {
            league = (League)bundle.getSerializable("league");
            leagueid = league.getId().toString();
        }




        fragment1 = new LeagueFightFragment();
        fragment2 = new LeagueScoreFragment();
        fragment3 = new LeagueTotalFragment();
        fragment4 = new LeagueReboundFragment();
        fragment5 = new LeagueAssistFragment();

        Bundle bundle2 = new Bundle();
        bundle2.putString("id", leagueid);

        fragment1.setArguments(bundle2);
        fragment2.setArguments(bundle2);
        fragment3.setArguments(bundle2);
        fragment4.setArguments(bundle2);
        fragment5.setArguments(bundle2);



        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        tab1.setSelected(true);

    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("联赛竞技");
        setTxtRight("加入");
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();

        Intent intent = new Intent(LeagueFightActivity.this,LeagueJoinActivity.class);
        //intent.putExtra("leagueid", leagueid);

        Bundle bundle = new Bundle();
        bundle.putSerializable("league",league);
        //intent.putExtra("teamid",teamid)

        intent.putExtras(bundle);

        startActivity(intent);

    }

    @OnClick({ R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4, R.id.tab5 })
    public void onClick(View v) {
        resetState(v.getId());
    }


    private void resetState(int id) {
        // 将三个按钮背景设置为未选中
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab3.setSelected(false);
        tab4.setSelected(false);
        tab5.setSelected(false);

        // 将点击的按钮背景设置为已选中
        switch (id) {
            case R.id.tab1:
                tab1.setSelected(true);
                switchContent(mContent,fragment1);
                break;
            case R.id.tab2:
                tab2.setSelected(true);
                switchContent(mContent, fragment2);
                break;
            case R.id.tab3:
                tab3.setSelected(true);
                switchContent(mContent,fragment3);
                break;
            case R.id.tab4:
                tab4.setSelected(true);
                switchContent(mContent,fragment4);
                break;

            case R.id.tab5:
                tab5.setSelected(true);
                switchContent(mContent,fragment5);
                break;
        }


        //fragment1
    }



    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.llContent, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }



}