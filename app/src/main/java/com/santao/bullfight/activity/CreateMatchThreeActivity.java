package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.santao.bullfight.R;
import com.santao.bullfight.event.ArenaEvent;
import com.santao.bullfight.event.BaseEvent;
import com.santao.bullfight.model.Arena;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CreateMatchThreeActivity extends AppCompatActivity {

    @Bind(R.id.txtPlace)
    TextView txtPlace;

    @Bind(R.id.txtDate)
    TextView txtDate;

    @Bind(R.id.txtStart)
    TextView txtStart;


    @Bind(R.id.txtEnd)
    TextView txtEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_three);

        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(ArenaEvent event) {

        Arena entity = (Arena)event.getData();

        if(entity==null)
        {
            return;
        }

        txtPlace.setText(entity.getName());

        //Log.d("harvic", msg);
    }


    @OnClick({R.id.layout1})
    public void placeList()
    {
        Intent intent = new Intent(CreateMatchThreeActivity.this, CreateMatchArenaActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.imgNext})
    public void goNext(View v)
    {
        Intent intent = new Intent(CreateMatchThreeActivity.this, CreateMatchFourActivity.class);
        startActivity(intent);
        //finish();
    }
}
