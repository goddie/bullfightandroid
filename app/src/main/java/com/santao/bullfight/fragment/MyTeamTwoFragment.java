package com.santao.bullfight.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.adapter.MyTeamAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTeamTwoFragment extends BaseFragment {

    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;
    private String uid=null;

    private MyTeamAdapter adpater;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_my_team_two;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle!=null){
            uid = bundle.getString("id");
        }



        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

        adpater = new MyTeamAdapter(getActivity());
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adapter.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adpater.getItemCount()) {
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

        adpater.setOnItemClickListener(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, Object id) {

//                TeamEvent event = new TeamEvent();
//                event.setData(tag);
//                EventBus.getDefault().post(event);

            }
        });


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
                adpater = new MyTeamAdapter(getActivity());
                recyclerView.setAdapter(adpater);
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
        String url = HttpUtil.getAbsoluteUrl("teamuser/json/myteam?uid=" + uid);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Team entity = gson.fromJson(jsonArray.get(i).toString(), Team.class);
                        list.add(entity);
                    }
                    adpater.addArrayList(list);
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
