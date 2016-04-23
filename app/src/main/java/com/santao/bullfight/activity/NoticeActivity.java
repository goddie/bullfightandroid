package com.santao.bullfight.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.santao.bullfight.adapter.NoticeAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.Message;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;
import com.santao.bullfight.widget.OnRecyclerViewItemLongClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeActivity extends BaseAppCompatActivity {


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;
    private String leagueid=null;

    private NoticeAdapter adapter;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ButterKnife.bind(this);

        initTopBar();




        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new NoticeAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adapter.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    getData();
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


                Message entity = (Message) id;

                updateRead(entity);


                if (entity.getType() == 1) {

                }


                //邀请入队
                if (entity.getType() == 2) {
                    join(entity);
                }


                if (entity.getType() == 3) {

                }

//                MatchFight entity = (MatchFight)id;
//                Intent intent = new Intent(NoticeActivity.this, MatchDetailActivity.class);
                //intent.putExtra("id", id.toString());

                //Log.d("","id:"+id);
                //leagueListAdapter.getArrayList().get(id);
//                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, Object tag) {
                final Message entity = (Message) tag;


                new android.support.v7.app.AlertDialog.Builder(NoticeActivity.this)
                        .setTitle("确认删除?")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                delete(entity);

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
        });



        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adapter = new NoticeAdapter(NoticeActivity.this);
//                recyclerView.setAdapter(adapter);
                adapter.clear();
                getData();

            }
        });

        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

//        getData();
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("消息");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus)
        {
            user = baseApplication.getLoginUser();
            getData();
        }

    }

    private void getData()
    {
        if(user==null)
        {
            return;
        }
        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("message/json/usermessage?uid="+user.getId().toString()+"&p="+page);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Message entity = gson.fromJson(jsonArray.get(i).toString(), Message.class);
                        list.add(entity);
                    }
                    adapter.addArrayList(list);
                    isLoadingMore = false;
                    page = page + 1;

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

    void join(final Message message)
    {

        final String tid = message.getTeam().getId().toString();
        final String uid = user.getId().toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否加入队伍 "+user.getNickname()+"?");
        builder.setTitle("加入队伍");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUtil.getAbsoluteUrl("teamuser/json/join?tid=" + tid
                        +"&uid="+user.getId().toString()), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int rs = jsonObject.getInt("code");


                            if(rs==1)
                            {

                                Toast.makeText(NoticeActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();

                                delete(message);

                            }else
                            {
                                Toast.makeText(NoticeActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
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


    void delete(Message entity)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUtil.getAbsoluteUrl("message/json/delete?uid="
                +user.getId().toString()+"&mid="+ entity.getId()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("code");

                    if(rs==1)
                    {
                        reload();
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


    void updateRead(Message entity)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUtil.getAbsoluteUrl("message/json/updateread?mid="+ entity.getId()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rs = jsonObject.getInt("code");

//                    if(rs==1)
//                    {
//                        Toast.makeText(NoticeActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
//
//                    }else
//                    {
//                        //Toast.makeText(MyTeamMemberActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
//                    }

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

    void reload()
    {
        page = 1;
        adapter.clear();
        getData();
    }
}
