package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.santao.bullfight.R;
import com.santao.bullfight.fragment.LeagueAssistFragment;
import com.santao.bullfight.fragment.LeagueFightFragment;
import com.santao.bullfight.fragment.LeagueReboundFragment;
import com.santao.bullfight.fragment.LeagueScoreFragment;
import com.santao.bullfight.fragment.LeagueTotalFragment;
import com.santao.bullfight.fragment.MyTeamOneFragment;
import com.santao.bullfight.fragment.MyTeamTwoFragment;
import com.santao.bullfight.model.League;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.TabButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTeamActivity extends BaseAppCompatActivity {




    @Bind(R.id.tab1)
    TabButton tab1;

    @Bind(R.id.tab2)
    TabButton tab2;


    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;

    private FragmentManager fragmentManager;


    private League league;
    String uid = null;
    //String teamid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_team);

        ButterKnife.bind(this);

        initTopBar();


        User user = baseApplication.getLoginUser();
        uid = user.getId().toString();

        Bundle bundle = getIntent().getExtras();


        fragment1 = new MyTeamOneFragment();
        fragment2 = new MyTeamTwoFragment();

        Bundle bundle2 = new Bundle();
        bundle2.putString("id", uid);

        fragment1.setArguments(bundle2);
        fragment2.setArguments(bundle2);



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
        setTitle("我的球队");
        setImgRight(R.mipmap.nav_btn_add);
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();

        Intent intent = new Intent(MyTeamActivity.this,CreateTeamActivity.class);

        startActivity(intent);

    }

    @OnClick({ R.id.tab1, R.id.tab2})
    public void onClick(View v) {
        resetState(v.getId());
    }


    private void resetState(int id) {
        // 将三个按钮背景设置为未选中
        tab1.setSelected(false);
        tab2.setSelected(false);


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
