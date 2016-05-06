package com.santao.bullfight.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.databinding.adapters.TimePickerBindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.santao.bullfight.R;
import com.santao.bullfight.event.ArenaEvent;
import com.santao.bullfight.event.BaseEvent;
import com.santao.bullfight.model.Arena;
import com.santao.bullfight.model.MatchFight;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CreateMatchThreeActivity extends BaseAppCompatActivity {

    @Bind(R.id.txtPlace)
    TextView txtPlace;

    @Bind(R.id.txtDate)
    TextView txtDate;

    @Bind(R.id.txtStart)
    TextView txtStart;


    @Bind(R.id.txtEnd)
    TextView txtEnd;

    @Bind(R.id.txtInfo)
    EditText txtInfo;


    private MatchFight matchFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_three);

        ButterKnife.bind(this);

        initTopBar();

        EventBus.getDefault().register(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("创建比赛");
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

        matchFight.setArena(entity);
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

        if(matchFight.getMatchType()==1)
        {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String date = txtDate.getText().toString();
            String start = txtStart.getText().toString();
            String end = txtEnd.getText().toString();

            Date dateStart = new Date();
            Date dateEnd = new Date();

            try {
                dateStart = sdf.parse(date +" " + start + ":00");
                dateEnd = sdf.parse(date +" " + end+":00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            matchFight.setStart(dateStart.getTime());
            matchFight.setEnd(dateEnd.getTime());


            String info ="";

            try {
                info = URLEncoder.encode(txtInfo.getText().toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            matchFight.setContent(info);

            Intent intent = new Intent(CreateMatchThreeActivity.this, CreateMatchFourActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("matchFight", matchFight);

            intent.putExtras(bundle);

            startActivity(intent);


            finish();
        }


        if(matchFight.getMatchType()==2)
        {
            finish();
        }


    }

    @OnClick({R.id.txtDate})
    public void txtDateClick()
    {
        selDate();
    }

    @OnClick({R.id.txtStart})
    public void txtTime1()
    {
        setTime(txtStart);
    }

    @OnClick({R.id.txtEnd})
    public void txtTime2()
    {
        setTime(txtEnd);
    }


    void setTime(final TextView txt)
    {
        Date date= new Date();

        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog timeDlg = new TimePickerDialog(CreateMatchThreeActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();

                calendar.set(1,1,1,hourOfDay,minute);

                Date date = new Date(calendar.getTimeInMillis());

                SimpleDateFormat sdf = new SimpleDateFormat("HH:ss");

                String s = sdf.format(date);

                txt.setText(s);

                Toast.makeText(CreateMatchThreeActivity.this, "确定时间: " + s, Toast.LENGTH_SHORT).show();


            }
        },12,0,true);

        timeDlg.setCanceledOnTouchOutside(true);

        timeDlg.show();



    }

    void selDate()
    {


        Date date= new Date();

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dateDlg = new DatePickerDialog(CreateMatchThreeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();

                calendar.set(year,monthOfYear,dayOfMonth);

                Date date = new Date(calendar.getTimeInMillis());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String s = sdf.format(date);

                txtDate.setText(s);

                Toast.makeText(CreateMatchThreeActivity.this, "确定日期: " + s, Toast.LENGTH_SHORT).show();


            }
        },date.getYear()+1900,date.getMonth(),date.getDate());

        dateDlg.setCanceledOnTouchOutside(true);

        dateDlg.show();

    }
}
