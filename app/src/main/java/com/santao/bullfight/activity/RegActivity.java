package com.santao.bullfight.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.League;
import com.santao.bullfight.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegActivity extends BaseAppCompatActivity {


    @Bind(R.id.inPhone)
    EditText phone;

    @Bind(R.id.inCode)
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);

        initTopBar();

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("注册");
    }

    @OnClick({R.id.txtLogin})
    public void login()
    {
        Intent intent = new Intent(RegActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btn1})
    public void sendSMS()
    {

        String phoneNum = phone.getText().toString();

        if(HttpUtil.isNullOrEmpty(phoneNum))
        {

            new  AlertDialog.Builder(RegActivity.this)
                    .setTitle("提醒" )
                    .setMessage("请输入手机号" )
                    .setPositiveButton("确定" ,  null )
                    .show();
            return;

        }

        String url = HttpUtil.getAbsoluteUrl("user/json/regsms?phone=" + phoneNum);
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
                        Toast.makeText(RegActivity.this, getString(R.string.sms_send), Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(RegActivity.this, getString(R.string.already_reg), Toast.LENGTH_SHORT).show();
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



    @OnClick({R.id.btn2})
    public void next()
    {

        String codeNum = code.getText().toString();
        String phoneNum = phone.getText().toString();

        if(HttpUtil.isNullOrEmpty(codeNum))
        {

            new  AlertDialog.Builder(RegActivity.this)
                    .setTitle("提醒" )
                    .setMessage("请输入获得的验证码" )
                    .setPositiveButton("确定" ,  null )
                    .show();
            return;

        }

        String url = HttpUtil.getAbsoluteUrl("user/json/regcheck?phone="+phoneNum+"&code=" + codeNum);
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

                        Intent intent = new Intent(RegActivity.this,RegTwoActivity.class);
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
