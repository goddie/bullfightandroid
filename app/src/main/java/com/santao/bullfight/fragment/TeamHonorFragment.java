package com.santao.bullfight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santao.bullfight.R;
import com.santao.bullfight.model.Team;

/**
 * 球队荣誉
 */
public class TeamHonorFragment extends BaseFragment {

    private  Team team;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_team_honor;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        Intent intent= getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        team = (Team)bundle.getSerializable("team");
    }
}
