package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.fragment.LeagueAssistFragment;
import com.santao.bullfight.fragment.LeagueFightFragment;
import com.santao.bullfight.fragment.LeagueReboundFragment;
import com.santao.bullfight.fragment.LeagueScoreFragment;
import com.santao.bullfight.fragment.LeagueTotalFragment;
import com.santao.bullfight.fragment.MatchInfoFragment;
import com.santao.bullfight.fragment.MatchInfoMessageFragment;
import com.santao.bullfight.fragment.MatchInfoTeamFragment;
import com.santao.bullfight.fragment.MatchInfoUserFragment;
import com.santao.bullfight.model.MatchFight;
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

public class MatchDetailActivity extends BaseAppCompatActivity {



    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.img2)
    ImageView img2;


    @Bind(R.id.txtTeam1)
    TextView txtTeam1;

    @Bind(R.id.txtTeam2)
    TextView txtTeam2;

    @Bind(R.id.txtScore)
    TextView txtScore;

    @Bind(R.id.txtNo1)
    TextView txtNo1;

    @Bind(R.id.txtNo2)
    TextView txtNo2;

    @Bind(R.id.txtTitle)
    TextView txtTitle;

    @Bind(R.id.txtDate)
    TextView txtDate;

    @Bind(R.id.txtTop)
    TextView txtTop;

    @Bind(R.id.imgTop)
    ImageView imgTop;



    @Bind(R.id.tab1)
    TabButton tab1;

    @Bind(R.id.tab2)
    TabButton tab2;

    @Bind(R.id.tab3)
    TabButton tab3;

    @Bind(R.id.tab4)
    TabButton tab4;


    @Bind(R.id.btn1)
    Button btn1;


    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;


    private FragmentManager fragmentManager;


    MatchFight entity=null;

    private Boolean isLeader = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);

        initTopBar();


        Bundle bundle=getIntent().getExtras();
        entity = (MatchFight)bundle.getSerializable("matchfight");

        bind();


        fragment1 = new MatchInfoFragment();
        fragment2 = new MatchInfoTeamFragment();
        fragment3 = new MatchInfoUserFragment();
        fragment4 = new MatchInfoMessageFragment();


//        Bundle bundle = new Bundle();
//        bundle.putString("id", matchid);
//
//        fragment1.setArguments(bundle);
//        fragment2.setArguments(bundle);
//        fragment3.setArguments(bundle);
//        fragment4.setArguments(bundle);




        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        tab1.setSelected(true);

        checkUser();
    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTxtRight("评论");

    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();

        Intent intent = new Intent(MatchDetailActivity.this,CommetAddActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("mfid",entity.getId().toString());

        intent.putExtras(bundle);

        startActivity(intent);
    }

    @OnClick({ R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4})
    public void onClick(View v) {
        resetState(v.getId());
    }


    private void resetState(int id) {
        // 将三个按钮背景设置为未选中
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab3.setSelected(false);
        tab4.setSelected(false);


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


    private void bind()
    {
        if(entity==null)
        {
            return;
        }

        if(null!=entity.getHost() && !HttpUtil.isNullOrEmpty(entity.getHost().getAvatar()))
        {
            Picasso.with(getApplicationContext()).load(HttpUtil.BASE_URL + entity.getHost().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(img1);
            txtTeam1.setText(entity.getHost().getName().toString());
        }else
        {
            Picasso.with(getApplicationContext()).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(img1);

        }

        if(null!=entity.getGuest() && !HttpUtil.isNullOrEmpty(entity.getGuest().getAvatar()))
        {
            Picasso.with(getApplicationContext()).load(HttpUtil.BASE_URL + entity.getGuest().getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder).into(img2);
            txtTeam2.setText(entity.getGuest().getName().toString());

        }else
        {
            Picasso.with(getApplicationContext()).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(img2);
        }


        if(entity.getArena()!=null)
        {
            txtTitle.setText(entity.getArena().getName().toString());
        }


        txtDate.setText( HttpUtil.getDate(entity.getStart()) );


        //未接招
        if(entity.getStatus()==0)
        {
            txtScore.setText("未接招");
            txtTop.setText("未接招");
            imgTop.setImageResource(R.mipmap.shared_icon_badge_unknow);

            txtTeam2.setText("");

            Picasso.with(getApplicationContext()).load(R.mipmap.feed_team_unknown).transform(new CircleTransform())
                    .into(img2);

            btn1.setVisibility(View.VISIBLE);

        }

        //未开始
        if(entity.getStatus()==1)
        {
            txtScore.setText("未开始");
            txtTop.setText("未开始");
            imgTop.setImageResource(R.mipmap.shared_icon_badge_active);

            btn1.setVisibility(View.GONE);
        }


        //已结束
        if(entity.getStatus()==2)
        {
            txtScore.setText(String.valueOf((int) entity.getHostScore()) + ":" + String.valueOf((int) entity.getGuestScore()));
            txtTop.setText("已结束");
            imgTop.setImageResource(R.mipmap.shared_icon_badge_inactive);

            btn1.setVisibility(View.GONE);
        }


        txtNo1.setText(String.valueOf(entity.getTeamSize()));
        txtNo2.setText(String.valueOf(entity.getTeamSize()));


    }


    //是否为队长
    void checkUser()
    {

        User user = Utils.getLocalUser(getApplicationContext());

        if(user ==null)
        {
            return ;
        }

        String url = HttpUtil.getAbsoluteUrl("team/json/checkuser?uid=" + user.getId() );
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");

                    if(code==1)
                    {
                        isLeader = true;
                        setButton();
                    }

                    //resize();


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


    void setButton()
    {
        if(isLeader)
        {
           btn1.setText("应战");
        }else
        {
           btn1.setText("通知队长");
        }
    }


    @OnClick({R.id.btn1})
    public void BtnClick()
    {

        User user = baseApplication.getLoginUser();

        if(isLeader)
        {


            Intent intent = new Intent(MatchDetailActivity.this,MatchAcceptActivity.class);

            //intent.putExtra("id", id.toString());

            Bundle bundle = new Bundle();
            bundle.putSerializable("matchfight",entity);

            intent.putExtras(bundle);

            //Log.d("","id:"+id);
            //leagueListAdapter.getArrayList().get(id);
            startActivity(intent);


//            Bundle bundle = new Bundle();
//            bundle.putSerializable("team",team);
//
//            intent.putExtras(bundle);

        }else
        {
            String url = HttpUtil.getAbsoluteUrl("message/json/sendtoteam?uid=" + user.getId() +"&mfid="+ entity.getId());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Gson gson = new Gson();
                    ArrayList<Object> list = new ArrayList<Object>();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("code");

                        if(code==1)
                        {
                            Toast.makeText(MatchDetailActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                        }

                        //resize();


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


}
