package com.santao.bullfight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.MatchDataTeam;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 已结束球队数据
 */
public class MatchInfoTeamFinishFragment extends BaseFragment {


    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.txt4)
    TextView txt4;


    @Bind(R.id.txt11)
    TextView txt11;

    @Bind(R.id.txt21)
    TextView txt21;

    @Bind(R.id.txt31)
    TextView txt31;

    @Bind(R.id.txt41)
    TextView txt41;

    @Bind(R.id.txt51)
    TextView txt51;


    @Bind(R.id.txt61)
    TextView txt61;



    @Bind(R.id.txt12)
    TextView txt12;

    @Bind(R.id.txt22)
    TextView txt22;

    @Bind(R.id.txt32)
    TextView txt32;

    @Bind(R.id.txt42)
    TextView txt42;

    @Bind(R.id.txt52)
    TextView txt52;


    @Bind(R.id.txt62)
    TextView txt62;



    @Override
    public int getContentViewId() {
        return R.layout.fragment_match_info_team_finish;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        getData();


    }


    private void getData()
    {

        Intent intent= getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        MatchFight entity = (MatchFight)bundle.getSerializable("matchfight");


        String url = HttpUtil.getAbsoluteUrl("matchdatateam/json/teamdata?mfid="+entity.getId().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    MatchDataTeam entity1= gson.fromJson(jsonArray.get(0).toString(), MatchDataTeam.class);
                    MatchDataTeam entity2 = gson.fromJson(jsonArray.get(1).toString(), MatchDataTeam.class);

                    bind(entity1,entity2);

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

    void bind(MatchDataTeam host,MatchDataTeam guest)
    {

        DecimalFormat decimalFormat=new DecimalFormat("0");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        txt1.setText(decimalFormat.format(host.getTeam().getPlayCount()));
        txt2.setText(decimalFormat.format(host.getTeam().getWin()));

        txt3.setText(decimalFormat.format(guest.getTeam().getPlayCount()));
        txt4.setText(decimalFormat.format(guest.getTeam().getWin()));

        txt11.setText(decimalFormat.format(host.getGoalPercent()*100)+"%");
        txt21.setText(decimalFormat.format(host.getFreeGoalPercent()*100)+"%");
        txt31.setText(decimalFormat.format(host.getThreeGoalPercent()*100)+"%");
        txt41.setText(decimalFormat.format(host.getRebound()));
        txt51.setText(decimalFormat.format(host.getAssist()));
        txt61.setText(decimalFormat.format(host.getSteal()));


        txt12.setText(decimalFormat.format(guest.getGoalPercent()*100)+"%");
        txt22.setText(decimalFormat.format(guest.getFreeGoalPercent()*100)+"%");
        txt32.setText(decimalFormat.format(guest.getThreeGoalPercent()*100)+"%");
        txt42.setText(decimalFormat.format(guest.getRebound()));
        txt52.setText(decimalFormat.format(guest.getAssist()));
        txt62.setText(decimalFormat.format(guest.getSteal()));

//        [dataArr2 addObject:@[@"历史战绩",[GlobalUtil toString:host.playCount],[GlobalUtil toString:host.win],[GlobalUtil toString:guest.playCount],[GlobalUtil toString:guest.win]]];
//        [dataArr2 addObject:@[@"投篮命中率",[GlobalUtil toPercentString:dataHost.goalPercent],[GlobalUtil toPercentString:dataGuest.goalPercent]]];
//        [dataArr2 addObject:@[@"罚球命中率",[GlobalUtil toPercentString:dataHost.freeGoalPercent],[GlobalUtil toPercentString:dataGuest.freeGoalPercent]]];
//        [dataArr2 addObject:@[@"三分命中率",[GlobalUtil toPercentString:dataHost.threeGoalPercent],[GlobalUtil toPercentString:dataGuest.threeGoalPercent]]];
//        [dataArr2 addObject:@[@"篮板",[GlobalUtil toString:dataHost.rebound],[GlobalUtil toString:dataGuest.rebound]]];
//        [dataArr2 addObject:@[@"助攻",[GlobalUtil toString:dataHost.assist],[GlobalUtil toString:dataGuest.assist]]];
//        [dataArr2 addObject:@[@"抢断",[GlobalUtil toString:dataHost.steal],[GlobalUtil toString:dataGuest.steal]]];

    }
}
