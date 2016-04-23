package com.santao.bullfight.activity;

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
import com.santao.bullfight.adapter.ArenaListAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.ArenaEvent;
import com.santao.bullfight.model.Arena;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class CreateMatchArenaActivity extends AppCompatActivity {



    @Bind(R.id.arenaList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    private ArenaListAdapter adapter;
    private int page = 1;
    private boolean isLoadingMore = false;
    private int matchType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_arena);


        ButterKnife.bind(this);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ArenaListAdapter(this);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object tag) {

                ArenaEvent event = new ArenaEvent();
                event.setData(tag);
                EventBus.getDefault().post(event);

                finish();

            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //Log.d("test", "StateChanged = " + newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d("test", "onScrolled");

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    //Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        getData();
                    }
                }
            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adapter = new ArenaListAdapter(CreateMatchArenaActivity.this);
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



    private void getData() {

        String url = HttpUtil.getAbsoluteUrl("arena/json/list?matchType="+matchType+"&p=" + page);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Arena entity = gson.fromJson(jsonArray.get(i).toString(), Arena.class);
                        list.add(entity);
                    }
                    adapter.addArrayList(list);
                    isLoadingMore = false;
                    page=page+1;

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
}
