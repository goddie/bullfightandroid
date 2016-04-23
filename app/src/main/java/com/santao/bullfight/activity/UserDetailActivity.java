package com.santao.bullfight.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.fragment.LeagueAssistFragment;
import com.santao.bullfight.fragment.LeagueFightFragment;
import com.santao.bullfight.fragment.LeagueReboundFragment;
import com.santao.bullfight.fragment.LeagueScoreFragment;
import com.santao.bullfight.fragment.LeagueTotalFragment;
import com.santao.bullfight.fragment.UserDetailDataFragment;
import com.santao.bullfight.fragment.UserDetailHonorFragment;
import com.santao.bullfight.fragment.UserDetailInfoFragment;
import com.santao.bullfight.model.League;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.santao.bullfight.widget.TabButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseAppCompatActivity {

    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.img2)
    ImageView img2;

    @Bind(R.id.img3)
    ImageView img3;


    @Bind(R.id.tab1)
    TabButton tab1;

    @Bind(R.id.tab2)
    TabButton tab2;

    @Bind(R.id.tab3)
    TabButton tab3;


    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;


    private FragmentManager fragmentManager;


    private User user;
    String userid = null;
    //String teamid = null;

    private ImageView[] teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        teams = new ImageView[]{img1, img2, img3};




        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("user")) {
            user = (User) bundle.getSerializable("user");
            userid = user.getId().toString();
        }


        fragment1 = new UserDetailInfoFragment();
        fragment2 = new UserDetailDataFragment();
        fragment3 = new UserDetailHonorFragment();


        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("user", user);


        fragment1.setArguments(bundle2);
        fragment2.setArguments(bundle2);
        fragment3.setArguments(bundle2);


        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        tab1.setSelected(true);

        getTeam();
    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle(user.getNickname());
    }


    @OnClick({R.id.tab1, R.id.tab2, R.id.tab3})
    public void onClick(View v) {
        resetState(v.getId());
    }


    private void resetState(int id) {
        // 将三个按钮背景设置为未选中
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab3.setSelected(false);


        // 将点击的按钮背景设置为已选中
        switch (id) {
            case R.id.tab1:
                tab1.setSelected(true);
                switchContent(mContent, fragment1);
                break;
            case R.id.tab2:
                tab2.setSelected(true);
                switchContent(mContent, fragment2);
                break;
            case R.id.tab3:
                tab3.setSelected(true);
                switchContent(mContent, fragment3);
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


    private void getTeam() {


        String url = HttpUtil.getAbsoluteUrl("teamuser/json/mytopteam?count=3&uid=" + user.getId().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Team entity = gson.fromJson(jsonArray.get(i).toString(), Team.class);
                        list.add(entity);


                    }

                    bindTeam(list);


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


    void bindTeam(ArrayList<Object> list)
    {
        for(int i=0;i<list.size();i++)
        {
            final Team entity = (Team)list.get(i);
            ImageView imageView  = teams[i];


            if(entity.getAvatar()!=null)
            {
                Picasso.with(UserDetailActivity.this).load(HttpUtil.BASE_URL + entity.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                        .into(imageView);


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(UserDetailActivity.this, TeamDetailActivity.class);
                        //intent.putExtra("leagueid", leagueid);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("team", entity);
                        //intent.putExtra("teamid",teamid)

                        intent.putExtras(bundle);

                        startActivity(intent);

                    }
                });




            }else
            {
                Picasso.with(UserDetailActivity.this).load(R.mipmap.holder).transform(new CircleTransform())
                        .into(imageView);
            }

        }
    }
}
