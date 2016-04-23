package com.santao.bullfight.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.MatchFightEvent;
import com.santao.bullfight.fragment.LeagueFragment;
import com.santao.bullfight.fragment.MatchInfoFragment;
import com.santao.bullfight.fragment.MatchTeamFragment;
import com.santao.bullfight.fragment.MatchWildFragment;
import com.santao.bullfight.fragment.UserCommetFragment;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.santao.bullfight.widget.TabButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MatchWildActivity extends BaseAppCompatActivity {

    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.tab1)
    TabButton tab1;

    @Bind(R.id.tab2)
    TabButton tab2;


    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;



    private FragmentManager fragmentManager;

    private User user;
    private MatchFight entity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_wild);
        ButterKnife.bind(this);

        initTopBar();



        fragment1 = new MatchInfoFragment();
        fragment2 = new UserCommetFragment();


        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        tab1.setSelected(true);




        Bundle bundle=getIntent().getExtras();
        entity = (MatchFight)bundle.getSerializable("matchfight");

        user = baseApplication.getLoginUser();
    }







    @OnClick({ R.id.tab1, R.id.tab2 })
    public void onClick(View v) {
        resetState(v.getId());
    }



    @OnClick({R.id.btn1})
    public void imgClick(View v)
    {




        String url = HttpUtil.getAbsoluteUrl("matchfightuser/json/join?uid="+user.getId().toString() + "&mfid="+entity.getId().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int rs = jsonObject.getInt("code");

                    if(rs==1)
                    {
                        Toast.makeText(MatchWildActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(MatchWildActivity.this, getString(R.string.action_fail), Toast.LENGTH_SHORT).show();
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


    void count()
    {

        List<User> list = new ArrayList<User>();

        String url = HttpUtil.getAbsoluteUrl("matchfightuser/json/listuser?count=6&mfid="+entity.getId().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int rs = jsonObject.getInt("code");

                    if(rs==1)
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User entity = gson.fromJson(jsonArray.get(i).toString(), User.class);
                            list.add(entity);
                        }

                        txt1.setText("最新报名");
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
