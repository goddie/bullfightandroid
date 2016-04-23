package com.santao.bullfight.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.santao.bullfight.adapter.CommetListAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.Commet;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 赛前动员
 */
public class MatchInfoMessageFragment extends BaseFragment {


    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;


    private CommetListAdapter adapter;
    private String mfid="";

    @Override
    public int getContentViewId() {
        return R.layout.fragment_match_info_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        Bundle bundle = getActivity().getIntent().getExtras();

        if(bundle!=null&&bundle.containsKey("matchfight")){
            MatchFight matchFight = (MatchFight)bundle.getSerializable("matchfight");
            mfid = matchFight.getId().toString();
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        adapter = new CommetListAdapter(getActivity(),1);
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


        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAppOrange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                page = 1;
//                adapter = new CommetListAdapter(getActivity(),1);
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

        String url = HttpUtil.getAbsoluteUrl("commet/json/list?mfid=" + mfid + "&p=" + page);
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
}
