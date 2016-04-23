package com.santao.bullfight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.Team;

import java.text.DecimalFormat;

import butterknife.Bind;

/**
 * 球队数据
 */
public class TeamDataFragment extends BaseFragment {

    private  Team team;


    @Bind(R.id.txt1)
    TextView txt1;


    @Bind(R.id.txt2)
    TextView txt2;


    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.txt4)
    TextView txt4;


    @Bind(R.id.txt5)
    TextView txt5;


    @Bind(R.id.txt6)
    TextView txt6;


    @Bind(R.id.txt7)
    TextView txt7;


    @Bind(R.id.txt8)
    TextView txt8;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_team_data;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Intent intent= getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        team = (Team)bundle.getSerializable("team");

        txt1.setText(fmt(team.getGoalPercent()*100)+"%");
        txt2.setText(fmt(team.getFreeGoalPercent()*100)+"%");
        txt3.setText(fmt(team.getRebound()));
        txt4.setText(fmt(team.getAssist()));
        txt5.setText(fmt(team.getBlock()));
        txt6.setText(fmt(team.getSteal()));
        txt7.setText(fmt(team.getTurnover()));
        txt8.setText(fmt(team.getFoul()));

    }


    String fmt(float f)
    {
        DecimalFormat df = new DecimalFormat("0");

        return df.format(f);
    }
}






//NSString *r1 =[GlobalUtil toPercentString:self.team.goalPercent];
//        NSString *r2 =[GlobalUtil toPercentString:self.team.freeGoalPercent];
//
//
//        NSString *r3 =[GlobalUtil toFloatString:self.team.rebound];
//        NSString *r4 =[GlobalUtil toFloatString:self.team.assist];
//
//        NSString *r5 =[GlobalUtil toFloatString:self.team.block];
//        NSString *r6 =[GlobalUtil toFloatString:self.team.steal];
//
//        NSString *r7 =[GlobalUtil toFloatString:self.team.turnover];
//        NSString *r8 =[GlobalUtil toFloatString:self.team.foul];