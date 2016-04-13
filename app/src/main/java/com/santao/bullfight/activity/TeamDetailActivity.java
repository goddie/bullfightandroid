package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.fragment.LeagueAssistFragment;
import com.santao.bullfight.fragment.LeagueFightFragment;
import com.santao.bullfight.fragment.LeagueReboundFragment;
import com.santao.bullfight.fragment.LeagueScoreFragment;
import com.santao.bullfight.fragment.LeagueTotalFragment;
import com.santao.bullfight.fragment.TeamDataFragment;
import com.santao.bullfight.fragment.TeamHonorFragment;
import com.santao.bullfight.fragment.TeamMemberFragment;
import com.santao.bullfight.fragment.TeamRecordFragment;
import com.santao.bullfight.model.League;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.santao.bullfight.widget.TabButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.awt.font.TextAttribute;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamDetailActivity extends BaseAppCompatActivity {


    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.btn1)
    Button btn1;

    @Bind(R.id.btn2)
    Button btn2;

    @Bind(R.id.tab1)
    TabButton tab1;

    @Bind(R.id.tab2)
    TabButton tab2;

    @Bind(R.id.tab3)
    TabButton tab3;

    @Bind(R.id.tab4)
    TabButton tab4;




    private Fragment mContent;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;


    private FragmentManager fragmentManager;

    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_mana);

        ButterKnife.bind(this);

        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("team")) {
            team = (Team)bundle.getSerializable("team");
        }




        fragment1 = new TeamRecordFragment();
        fragment2 = new TeamMemberFragment();
        fragment3 = new TeamDataFragment();
        fragment4 = new TeamHonorFragment();


        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("team",team);


        fragment1.setArguments(bundle2);
        fragment2.setArguments(bundle2);
        fragment3.setArguments(bundle2);
        fragment4.setArguments(bundle2);




        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        transaction.add(R.id.llContent, fragment1);
        transaction.commit();

        mContent = fragment1;

        tab1.setSelected(true);

        bind();

        checkAdmin();
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle(team.getName());
    }


    @OnClick({ R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4})
    public void onClick(View v) {
        resetState(v.getId());
    }


    private void resetState(int id) {
        // 将三个按钮背景设置为未选中
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab3.setSelected(false);
        tab4.setSelected(false);


        // 将点击的按钮背景设置为已选中
        switch (id) {
            case R.id.tab1:
                tab1.setSelected(true);
                switchContent(mContent,fragment1);
                break;
            case R.id.tab2:
                tab2.setSelected(true);
                switchContent(mContent, fragment2);
                break;
            case R.id.tab3:
                tab3.setSelected(true);
                switchContent(mContent,fragment3);
                break;
            case R.id.tab4:
                tab4.setSelected(true);
                switchContent(mContent,fragment4);
                break;
        }


        //fragment1
    }



    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.llContent, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


    void bind()
    {
        if(team==null)
        {
            return;

        }

        txt1.setText(team.getName());
        txt2.setText(HttpUtil.getDate(team.getCreatedDate()));
        txt3.setText(team.getInfo());

        if(team.getAvatar()!=null)
        {
            Picasso.with(this).load(HttpUtil.BASE_URL + team.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                    .into(img1);

        }else
        {
            Picasso.with(this).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(img1);
        }



    }



    void checkAdmin()
    {
        User user = Utils.getLocalUser(this);
        if(user==null)
        {
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
        }

        if(team!=null&&!user.getId().toString().equals(team.getAdmin().getId().toString()))
        {
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn1})
    public void edit()
    {
        Intent intent = new Intent(TeamDetailActivity.this,MyTeamEditActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("team",team);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    @OnClick({R.id.btn2})
    public void invite()
    {
        Intent intent = new Intent(TeamDetailActivity.this,MyTeamMemberActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("team",team);

        intent.putExtras(bundle);

        startActivity(intent);
    }


}
