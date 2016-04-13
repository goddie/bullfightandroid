package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.event.TeamEvent;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CreateMatchTwoActivity extends BaseAppCompatActivity {


    @Bind(R.id.imgTeam)
    ImageView imgTeam;

    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.img3)
    ImageView img3;

    @Bind(R.id.img4)
    ImageView img4;

    @Bind(R.id.img5)
    ImageView img5;


    private MatchFight matchFight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_two);


        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        baseApplication.getLoginUser();


        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }

    }


    @OnClick({R.id.imgTeam})
    public void selTeam(View v)
    {
        Intent intent = new Intent(CreateMatchTwoActivity.this, CreateMatchTeamActivity.class);

        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(TeamEvent event) {

        Team entity = (Team)event.getData();

        if(entity==null || entity.getAvatar()==null || entity.getAvatar().equals(""))
        {
            return;
        }

        Picasso.with(getApplicationContext()).load(HttpUtil.BASE_URL + entity.getAvatar()).placeholder(R.mipmap.holder)
                .into(imgTeam);

        //Log.d("harvic", msg);
    }



    @OnClick({R.id.img1,R.id.img3,R.id.img4,R.id.img5})
    public void imgCreateClick(View v)
    {

        img1.setImageResource(R.mipmap.shared_picker_unselected);
        img3.setImageResource(R.mipmap.shared_picker_unselected);
        img4.setImageResource(R.mipmap.shared_picker_unselected);
        img5.setImageResource(R.mipmap.shared_picker_unselected);

        ImageView imageView = (ImageView)v;
        imageView.setImageResource(R.mipmap.shared_picker_selected);
    }



    @OnClick({R.id.imgNext})
    public void goNext(View v)
    {
        Intent intent = new Intent(CreateMatchTwoActivity.this, CreateMatchThreeActivity.class);


        Bundle bundle = new Bundle();
        bundle.putSerializable("matchFight", matchFight);

        intent.putExtras(bundle);

        startActivity(intent);
    }
}
