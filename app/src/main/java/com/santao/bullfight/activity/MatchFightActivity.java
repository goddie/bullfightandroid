package com.santao.bullfight.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.event.MatchFightEvent;
import com.santao.bullfight.event.PageEvent;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.fragment.LeagueFragment;
import com.santao.bullfight.fragment.MatchTeamFragment;
import com.santao.bullfight.fragment.MatchWildFragment;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.santao.bullfight.widget.TabButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class MatchFightActivity extends BaseAppCompatActivity {


    @Bind(R.id.btn_left)
    TabButton left;

    @Bind(R.id.btn_center)
    TabButton center;

    @Bind(R.id.btn_right)
    TabButton right;


    @Bind(R.id.imgLeft)
    ImageView imgLeft;

    @Bind(R.id.imgRight)
    ImageView imgRight;

    @Bind(R.id.mainLayout)
    FrameLayout mainLayout;

@Bind(R.id.red)
ImageView red;



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

        EventBus.getDefault().register(this);

        init();



        fragment1 = new MatchTeamFragment();
        fragment2 = new MatchWildFragment();
        fragment3 = new LeagueFragment();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        left.setSelected(true);




//        ViewTreeObserver vto = mainLayout.getViewTreeObserver();
//
//
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//                // remove the listener so it won't get called again if the view layout changes
//                mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                // add your calculations here
//                getNotice();
//            }});


        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                getNotice();
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);

    }


    public void init() {


        imgLeft.setImageResource(R.mipmap.icon_60);
        imgRight.setImageResource(R.mipmap.nav_filter);


        if(baseApplication.isLogin())
        {
            User user = baseApplication.getLoginUser();


            if(!HttpUtil.isNullOrEmpty(user))
            {
                Picasso.with(MatchFightActivity.this).load(HttpUtil.BASE_URL + user.getAvatar()).transform(new CircleTransform())
                        .placeholder(R.mipmap.icon_60).into(imgLeft);
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.imgLeft})
    public void leftClick() {
        super.onTopLeftClick();

        Intent intent = new Intent(MatchFightActivity.this, NoticeActivity.class);

        startActivity(intent);
    }


    @OnClick({R.id.imgRight})
    public void rightClick() {

        String[] items =new String[]{"全部比赛","待应战","未开始","已结束"};

        new AlertDialog.Builder(MatchFightActivity.this)
                .setTitle("请点击选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MatchFightEvent event = new MatchFightEvent(MatchFightEvent.MATCHE_FILTER);
                        event.setData(which);
                        EventBus.getDefault().post(event);

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    @OnClick({ R.id.btn_left, R.id.btn_right, R.id.btn_center })
    public void onClick(View v) {
        resetState(v.getId());
    }



    @OnClick({R.id.imgCreate})
    public void imgClick(View v)
    {
        Intent intent = new Intent(MatchFightActivity.this, CreateMatchOneActivity.class);

        MatchFight matchFight = new MatchFight();

        //matchFight.setMatchType(2);

        Bundle bundle = new Bundle();
        bundle.putSerializable("matchFight", matchFight);

        intent.putExtras(bundle);

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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        getNotice();

    }


    public void onEventMainThread(PageEvent event) {

//        int idx = (int)event.getData();
//
//        //Log.d("page change=====", idx+"");
//
//        if(idx==0)
//        {
//            getNotice();
//        }
    }


    void getNotice()
    {

        Log.d("getNotice","getNotice");

        User user  = Utils.getLocalUser(MatchFightActivity.this);

        if(user==null)
        {
            return;
        }

        String url = HttpUtil.getAbsoluteUrl("message/json/countnew?uid=" + user.getId().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int rs = jsonObject.getInt("data");

                    if(rs>0)
                    {
                        red.setVisibility(View.VISIBLE);
                    }else
                    {
                        red.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        BaseApplication.getHttpQueue().add(stringRequest);
    }



}
