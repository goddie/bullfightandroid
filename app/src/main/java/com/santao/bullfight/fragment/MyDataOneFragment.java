package com.santao.bullfight.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.model.User;

import java.text.DecimalFormat;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDataOneFragment extends BaseFragment {

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

    @Bind(R.id.txt9)
    TextView txt9;

    @Bind(R.id.txt10)
    TextView txt10;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_my_data_one;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        User user = BaseApplication.getBaseApplication().getLoginUser();

        DecimalFormat df =new DecimalFormat("0.0");
        DecimalFormat df2 =new DecimalFormat("0");

        txt1.setText(df2.format(user.getPlayCount()));
        txt2.setText(df2.format(user.getScoring()));
        txt3.setText(df.format(user.getScoringAvg()));
        txt4.setText(df.format(user.getGoalPercent()*100)+"%");
        txt5.setText(df.format(user.getFreeGoalPercent()*100)+"%");
        txt6.setText(df.format(user.getThreeGoalPercent()*100)+"%");
        txt7.setText(df.format(user.getRebound()));
        txt8.setText(df.format(user.getAssist()));
        txt9.setText(df.format(user.getBlock()));
        txt10.setText(df.format(user.getSteal()));
    }


}
