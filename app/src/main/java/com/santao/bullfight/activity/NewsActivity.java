package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.NewsListAdapter;
import com.santao.bullfight.fragment.LeagueAssistFragment;
import com.santao.bullfight.fragment.LeagueFightFragment;
import com.santao.bullfight.fragment.LeagueReboundFragment;
import com.santao.bullfight.fragment.LeagueScoreFragment;
import com.santao.bullfight.fragment.LeagueTotalFragment;
import com.santao.bullfight.fragment.NewsFragment;
import com.santao.bullfight.model.Article;
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

public class NewsActivity extends BaseAppCompatActivity {

    @Bind(R.id.btn_left)
    TabButton left;

    @Bind(R.id.btn_center)
    TabButton center;

    @Bind(R.id.btn_right)
    TabButton right;

    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;

    private FragmentManager fragmentManager;

    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        ButterKnife.bind(this);

        initTopBar();

        fragment1 = new NewsFragment();
        fragment2 = new NewsFragment();
        fragment3 = new NewsFragment();


        Bundle b1 = new Bundle();
        b1.putInt("type", 1);

        Bundle b2 = new Bundle();
        b2.putInt("type", 2);

        Bundle b3 = new Bundle();
        b3.putInt("type", 3);

        fragment1.setArguments(b1);
        fragment2.setArguments(b2);
        fragment3.setArguments(b3);




        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;


        left.setSelected(true);

    }



    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("新闻");
    }

    @OnClick({ R.id.btn_left, R.id.btn_right, R.id.btn_center })
    public void onClick(View v) {
        resetState(v.getId());
    }


    private void resetState(int id) {
        // 将三个按钮背景设置为未选中
        left.setSelected(false);
        center.setSelected(false);
        right.setSelected(false);

        // 将点击的按钮背景设置为已选中
        switch (id) {
            case R.id.btn_left:
                left.setSelected(true);
                switchContent(mContent, fragment1);
                break;
            case R.id.btn_center:
                center.setSelected(true);
                switchContent(mContent, fragment2);
                break;
            case R.id.btn_right:
                right.setSelected(true);
                switchContent(mContent, fragment3);
                break;
        }




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
