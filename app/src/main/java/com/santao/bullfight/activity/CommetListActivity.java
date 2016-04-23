package com.santao.bullfight.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.CommetListAdapter;
import com.santao.bullfight.adapter.NewsListAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.MatchFightEvent;
import com.santao.bullfight.model.Article;
import com.santao.bullfight.model.Commet;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class CommetListActivity extends BaseAppCompatActivity {



    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;


    private CommetListAdapter adapter;
    private String aid="";
//    private String mfid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commet_list);

        ButterKnife.bind(this);

        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null&&bundle.containsKey("aid")){
            aid = bundle.getString("aid");
        }


//        if(bundle!=null&&bundle.containsKey("mfid")){
//            mfid = bundle.getString("mfid");
//        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(CommetListActivity.this);

        adapter = new CommetListAdapter(CommetListActivity.this);
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

                final Commet entty = (Commet) id;

                new android.support.v7.app.AlertDialog.Builder(CommetListActivity.this)
                        .setTitle("是否回复")
                        .setPositiveButton("回复", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Intent intent = new Intent(CommetListActivity.this, CommetAddActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("aid", aid);
                                bundle.putString("ruid", entty.getFrom().getId().toString());

                                intent.putExtras(bundle);

                                startActivity(intent);


                            }
                        })
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

//                Article entity = (Article)id;
//
//                Intent intent = new Intent(CommetListActivity.this, NewsDetailActivity.class);
//                intent.putExtra("id", entity.getId().toString());
//
//
//                //Log.d("","id:"+id);
//                //leagueListAdapter.getArrayList().get(id);
//                startActivity(intent);
            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(CommetListActivity.this, R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adapter = new CommetListAdapter(CommetListActivity.this);
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


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("评论");
        setTxtRight("发表");
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();
        Intent intent =new Intent(CommetListActivity.this,CommetAddActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("aid",aid);


        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void getData() {

        String url = HttpUtil.getAbsoluteUrl("commet/json/list?aid=" + aid + "&p=" + page);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Commet entity = gson.fromJson(jsonArray.get(i).toString(), Commet.class);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
//            page = 1;
//            adapter = new CommetListAdapter(CommetListActivity.this);
//            recyclerView.setAdapter(adapter);
//            getData();
        }
    }
}
