package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

public class RegTwoActivity extends BaseAppCompatActivity {

    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_two);
        ButterKnife.bind(this);

        initTopBar();



    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("注册");
    }

    @OnClick({R.id.btn1})
    public  void next()
    {
        User user = baseApplication.getLoginUser();

        String username = "";
        try {
            username = URLEncoder.encode(txt1.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String password =  "";
        try {
            password = URLEncoder.encode(txt2.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String nickname =  "";
        try {
            nickname = URLEncoder.encode(txt3.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(user==null || HttpUtil.isNullOrEmpty(username) || HttpUtil.isNullOrEmpty(password) || HttpUtil.isNullOrEmpty(nickname))
        {

            new  AlertDialog.Builder(RegTwoActivity.this)
                    .setTitle("提醒")
                    .setMessage("请输入个人信息" )
                    .setPositiveButton("确定" ,  null )
                    .show();
            return;

        }

        String url = HttpUtil.getAbsoluteUrl("user/json/regtwo?username="+username+"&password=" + password+"&nickname="+nickname+"&uid="+user.getId().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int rs = jsonObject.getInt("code");

                    if(rs==1)
                    {

                        JSONObject obj = jsonObject.getJSONObject("data");
                        User entity = gson.fromJson(obj.toString(), User.class);

                        baseApplication.setLoginUser(entity);

                        Intent intent = new Intent(RegTwoActivity.this,RegThreeActivity.class);
                        startActivity(intent);
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
