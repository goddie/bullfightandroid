package com.santao.bullfight.activity;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.santao.bullfight.R;
import com.santao.bullfight.fragment.LeagueFragment;
import com.santao.bullfight.fragment.MatchTeamFragment;
import com.santao.bullfight.fragment.MatchWildFragment;
import com.santao.bullfight.widget.TabButton;


import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;


public class MatchFightActivity extends BaseAppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        ButterKnife.bind(this);


        fragment1 = new MatchTeamFragment();
        fragment2 = new MatchWildFragment();
        fragment3 = new LeagueFragment();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        left.setSelected(true);

    }


    @OnClick({ R.id.btn_left, R.id.btn_right, R.id.btn_center })
    public void onClick(View v) {
        resetState(v.getId());
    }



    @OnClick({R.id.imgCreate})
    public void imgClick(View v)
    {
        Intent intent = new Intent(MatchFightActivity.this, CreateMatchOneActivity.class);

        startActivity(intent);
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
                switchContent(mContent,fragment1);
                break;
            case R.id.btn_center:
                center.setSelected(true);
                switchContent(mContent, fragment2);
                break;
            case R.id.btn_right:
                right.setSelected(true);
                switchContent(mContent,fragment3);
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
