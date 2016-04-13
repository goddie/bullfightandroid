package com.santao.bullfight.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseAppCompatActivity {


    @Bind({R.id.txt1})
    TextView txt1;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initTopBar();
        user = baseApplication.getLoginUser();
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("意见反馈");
    }



    @OnClick({R.id.btn1})
    public void btn1Click()
    {
        String content = txt1.getText().toString();


        if(HttpUtil.isNullOrEmpty(content))
        {
            new  AlertDialog.Builder(FeedbackActivity.this)
                    .setTitle("提醒" )
                    .setMessage("请输入反馈内容" )
                    .setPositiveButton("确定" ,  null )
                    .show();
            return;
        }


        String url = null;
        try {
            url = HttpUtil.getAbsoluteUrl("feedback/json/add?uid=" + user.getId().toString() + "&content=" + URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int rs = jsonObject.getInt("code");

                    if(rs==1)
                    {
                        Toast.makeText(FeedbackActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                        finish();
                    }

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

}
