package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.adapter.NewsListAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.UserListAdapter;
import com.santao.bullfight.model.Article;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendActivity extends BaseAppCompatActivity {


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;

    private List<User> data;

    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        ButterKnife.bind(this);
        initTopBar();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new UserListAdapter(this);
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

                User entity = (User)id;

                Intent intent = new Intent(FriendActivity.this, UserDetailActivity.class);
                //intent.putExtra("id", entity.getId().toString());

                Bundle bundle = new Bundle();
                bundle.putSerializable("user",entity);

                intent.putExtras(bundle);

                //Log.d("","id:"+id);
                //leagueListAdapter.getArrayList().get(id);
                startActivity(intent);
            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                adapter = new UserListAdapter(FriendActivity.this);
                recyclerView.setAdapter(adapter);
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
        setTitle("牛丸圈");
    }


    private void getData() {
        isLoadingMore = true;
        String url = HttpUtil.getAbsoluteUrl("user/json/list?p="+page);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<>();
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