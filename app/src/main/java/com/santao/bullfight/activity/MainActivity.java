package com.santao.bullfight.activity;


import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santao.bullfight.adapter.ContentAdapter;
import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.core.UpdateChecker;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatActivity implements OnClickListener,OnPageChangeListener{

    // 底部菜单4个Linearlayout
    @Bind(R.id.ll_tab1) LinearLayout ll_tab1;
    @Bind(R.id.ll_tab2) LinearLayout ll_tab2;
    @Bind(R.id.ll_tab3) LinearLayout ll_tab3;
    @Bind(R.id.ll_tab4) LinearLayout ll_tab4;

    // 底部菜单4个ImageView
    @Bind(R.id.iv_tab1) ImageView iv_tab1;
    @Bind(R.id.iv_tab2) ImageView iv_tab2;
    @Bind(R.id.iv_tab3) ImageView iv_tab3;
    @Bind(R.id.iv_tab4) ImageView iv_tab4;


    // 底部菜单4个菜单标题
    @Bind(R.id.tv_tab1) TextView tv_tab1;
    @Bind(R.id.tv_tab2) TextView tv_tab2;
    @Bind(R.id.tv_tab3) TextView tv_tab3;
    @Bind(R.id.tv_tab4) TextView tv_tab4;



    // 中间内容区域
    // @Bind(R.id.vp_content)
    @Bind(R.id.vp_content)  ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;

    private List<View> views;

    private LocalActivityManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTopBar();


        manager = new LocalActivityManager(this , true);
        manager.dispatchCreate(savedInstanceState);



        ButterKnife.bind(this);
        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();

        onPageSelected(0);

        UpdateChecker updateChecker = new UpdateChecker(MainActivity.this);
        updateChecker.setCheckUrl(HttpUtil.getAbsoluteUrl("appversion/json/getlast"));
        updateChecker.checkForUpdates();

    }

    private void initEvent() {
        // 设置按钮监听
        ll_tab1.setOnClickListener(this);
        ll_tab2.setOnClickListener(this);
        ll_tab3.setOnClickListener(this);
        ll_tab4.setOnClickListener(this);

        //设置ViewPager滑动监听
        viewPager.addOnPageChangeListener(this);
//



    }

    private void initView() {

        views = new ArrayList<View>();

        // 适配器

        Intent intent1 = new Intent(MainActivity.this, MatchFightActivity.class);
        views.add(getView("MatchFightActivity", intent1));
        Intent intent2 = new Intent(MainActivity.this, NewsActivity.class);
        views.add(getView("NewsActivity", intent2));
        Intent intent3 = new Intent(MainActivity.this, FriendActivity.class);
        views.add(getView("FriendActivity", intent3));
        Intent intent4 = new Intent(MainActivity.this, ProfileActivity.class);
        views.add(getView("ProfileActivity", intent4));





        this.adapter = new ContentAdapter(views);

        viewPager.setAdapter(adapter);

    }


    /**
     * 通过activity获取视图
     * @param id
     * @param intent
     * @return
     */
    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    @Override
    public void onClick(View v) {
        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.ll_tab1:
                iv_tab1.setImageResource(R.mipmap.tab_icon_match_active);
                tv_tab1.setTextColor(getResources().getColor(R.color.colorAppOrange));
                viewPager.setCurrentItem(0);

                break;
            case R.id.ll_tab2:
                iv_tab2.setImageResource(R.mipmap.tab_icon_news_active);
                tv_tab2.setTextColor(getResources().getColor(R.color.colorAppOrange));
                viewPager.setCurrentItem(1);

                break;
            case R.id.ll_tab3:
                iv_tab3.setImageResource(R.mipmap.tab_icon_activities_active);
                tv_tab3.setTextColor(getResources().getColor(R.color.colorAppOrange));
                viewPager.setCurrentItem(2);

                break;
            case R.id.ll_tab4:
                iv_tab4.setImageResource(R.mipmap.tab_icon_profile_active);
                tv_tab4.setTextColor(getResources().getColor(R.color.colorAppOrange));
                viewPager.setCurrentItem(3);

                break;

            default:
                break;
        }

    }

    private void restartBotton() {

        // ImageView置为灰色
        iv_tab1.setImageResource(R.mipmap.tab_icon_match);
        iv_tab2.setImageResource(R.mipmap.tab_icon_news);
        iv_tab3.setImageResource(R.mipmap.tab_icon_activities);
        iv_tab4.setImageResource(R.mipmap.tab_icon_profile);


        // TextView置为白色
        tv_tab1.setTextColor(0xffffffff);
        tv_tab2.setTextColor(0xffffffff);
        tv_tab3.setTextColor(0xffffffff);
        tv_tab4.setTextColor(0xffffffff);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        restartBotton();
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (arg0) {
            case 0:
                iv_tab1.setImageResource(R.mipmap.tab_icon_match_active);
                tv_tab1.setTextColor(getResources().getColor(R.color.colorAppOrange));

                break;
            case 1:
                iv_tab2.setImageResource(R.mipmap.tab_icon_news_active);
                tv_tab2.setTextColor(getResources().getColor(R.color.colorAppOrange));

                break;
            case 2:
                iv_tab3.setImageResource(R.mipmap.tab_icon_activities_active);
                tv_tab3.setTextColor(getResources().getColor(R.color.colorAppOrange));

                break;
            case 3:
                //Log.d("", "--------- onPageSelected");
                iv_tab4.setImageResource(R.mipmap.tab_icon_profile_active);
                tv_tab4.setTextColor(getResources().getColor(R.color.colorAppOrange));

                break;

            default:
                break;
        }

    }

}








