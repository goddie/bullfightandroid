package com.santao.bullfight.fragment;


import android.content.Intent;
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
import com.santao.bullfight.activity.MatchDetailActivity;
import com.santao.bullfight.adapter.MatchInfoUserAdapter;
import com.santao.bullfight.adapter.MatchInfoUserFinishAdapter;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.MatchDataUser;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchInfoUserFinishFragment extends BaseFragment {





    @Bind(R.id.itemList)
    RecyclerView recyclerView;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private  int page=1;
    private boolean isLoadingMore = false;


    private MatchInfoUserFinishAdapter adpater;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_match_info_user_finish;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        adpater = new MatchInfoUserFinishAdapter(getActivity());
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                //Log.d("", "newState:" + newState + " " + adpater.getItemCount());

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adpater.getItemCount()) {
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

        adpater.setOnItemClickListener(new OnRecyclerViewItemClickListener() {

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
                adpater = new MatchInfoUserFinishAdapter(getActivity());
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

        Intent intent= getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        MatchFight entity = (MatchFight)bundle.getSerializable("matchfight");

        String url = HttpUtil.getAbsoluteUrl("matchdatauser/json/userdata?mfid="+entity.getId().toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                    JSONArray subArr1 = jsonArray.getJSONArray(0);
                    JSONArray subArr2 = jsonArray.getJSONArray(1);


                    ArrayList<MatchDataUser> arr1 = new ArrayList<>();
                    ArrayList<MatchDataUser> arr2 = new ArrayList<>();

                    for (int i=0;i<subArr1.length();i++)
                    {
                        MatchDataUser entity = gson.fromJson(subArr1.get(i).toString(),MatchDataUser.class);
                        arr1.add(entity);
                    }

                    for (int j=0;j<subArr2.length();j++)
                    {
                        MatchDataUser entity = gson.fromJson(subArr2.get(j).toString(), MatchDataUser.class);
                        arr2.add(entity);
                    }



                    int s = Math.min(arr1.size(),arr2.size());


                    for (int i=0;i<s;i++)
                    {
                        ArrayList<Object> tmp = new ArrayList<Object>();

                        tmp.add(arr1.get(i));
                        tmp.add(arr2.get(i));

                        list.add(tmp);
                    }

                    if(arr1.size()>arr2.size())
                    {
                        ArrayList<Object> tmp = new ArrayList<Object>();
                        tmp.add(arr1.get(arr1.size()-1));
                        tmp.add(null);
                        list.add(tmp);

                    }else
                    {
                        ArrayList<Object> tmp = new ArrayList<Object>();
                        tmp.add(null);
                        tmp.add(arr2.get(arr2.size()-1));
                        list.add(tmp);
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
