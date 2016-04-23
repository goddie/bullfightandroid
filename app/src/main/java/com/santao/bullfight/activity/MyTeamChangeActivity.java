package com.santao.bullfight.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.MyTeamMemberAdapter;
import com.santao.bullfight.adapter.TeamMemberChangeAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.MemberEvent;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MyTeamChangeActivity extends BaseAppCompatActivity {

    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;

    private List<User> data;

    private TeamMemberChangeAdapter adapter;

    private Team team;

    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_change);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initTopBar();

        loginUser = baseApplication.getLoginUser();

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        team = (Team)bundle.getSerializable("team");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new TeamMemberChangeAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adpater.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    //getData();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d("test", "onScrolled");

                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                //Log.d("","lastVisibleItem"+lastVisibleItem);
            }
        });

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, Object id) {


//                MatchFight entity = (MatchFight)id;
//
//                Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
//
//                Bundle mBundle = new Bundle();
//                mBundle.putSerializable("matchfight",entity);
//
//                intent.putExtras(mBundle);
//
//                //Log.d("","id:"+id);
//                //leagueListAdapter.getArrayList().get(id);
//                startActivity(intent);
            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adapter = new TeamMemberChangeAdapter(MyTeamChangeActivity.this);
//                recyclerView.setAdapter(adapter);
                adapter.clear();
                getData();

            }
        });

        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        getData();

    }

    private void getData()
    {

        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("teamuser/json/memberlist?tid=" + team.getId().toString() + "&page=" + page);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        User entity = gson.fromJson(jsonArray.get(i).toString(), User.class);
                        list.add(entity);
                    }
                    adapter.addArrayList(list);
                    isLoadingMore = false;
                    page = page + 1;

                    //resize();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        BaseApplication.getHttpQueue().add(stringRequest);
    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("更换队长");
    }

//
//    @OnClick({R.id.btn1})
//    public  void  btn1Click()
//    {
//        Intent intent = new Intent(MyTeamChangeActivity.this,TeamMemberAddActivity.class);
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("team",team);
//
//        intent.putExtras(bundle);
//
//        startActivity(intent);
//    }
//
//
//    @OnClick({R.id.btn2})
//    public  void  btn2Click()
//    {
//        Intent intent = new Intent(MyTeamChangeActivity.this,MyTeamEditActivity.class);
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("team",team);
//
//        intent.putExtras(bundle);
//
//        startActivity(intent);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MemberEvent event) {

        if(event.getEventName().equals(MemberEvent.MEMBER_CAPTAIN))
        {
            final User user = (User)event.getData();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确认更换队长为 "+user.getNickname()+"?");
            builder.setTitle("更换队长");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUtil.getAbsoluteUrl("team/json/admin?tid="
                            + team.getId().toString()+"&newid="
                            +user.getId().toString()+"&oldid="
                            +loginUser.getId().toString()), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int rs = jsonObject.getInt("code");

                                if(rs==1)
                                {
                                    Toast.makeText(MyTeamChangeActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();

                                }else
                                {
                                    //Toast.makeText(MyTeamMemberActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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

                    dialog.dismiss();

                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();

//            alertDialog.setCancelable(false);//设置这个对话框不能被用户按[返回键]而取消掉,但测试发现如果用户按了KeyEvent.KEYCODE_SEARCH,对话框还是会Dismiss掉
//            //由于设置alertDialog.setCancelable(false); 发现如果用户按了KeyEvent.KEYCODE_SEARCH,对话框还是会Dismiss掉,这里的setOnKeyListener作用就是屏蔽用户按下KeyEvent.KEYCODE_SEARCH
//            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
//                {
//                    if (keyCode == KeyEvent.KEYCODE_SEARCH)
//                    {
//                        return true;
//                    }
//                    else
//                    {
//                        return false; //默认返回 false
//                    }
//                }
//            });

            alertDialog.show();

        }



        //Log.d("harvic", msg);
    }
}
