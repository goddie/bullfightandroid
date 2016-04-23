package com.santao.bullfight.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordOneActivity extends BaseAppCompatActivity {



    @Bind({R.id.txt1})
    TextView txt1;

    @Bind({R.id.txt2})
    TextView txt2;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_one);

        ButterKnife.bind(this);

        initTopBar();

        user = baseApplication.getLoginUser();
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("修改密码");
    }


    @OnClick({R.id.btn1})
    public void btn1Click()
    {
        String password1 = txt1.getText().toString();
        String password2 = txt2.getText().toString();

        if(!password1.equals(password2))
        {
            new  AlertDialog.Builder(PasswordOneActivity.this)
                    .setTitle("提醒" )
                    .setMessage("两次密码不一致" )
                    .setPositiveButton("确定" ,  null )
                    .show();
            return;
        }

        if(HttpUtil.isNullOrEmpty(password1)||HttpUtil.isNullOrEmpty(password2))
        {

            new  AlertDialog.Builder(PasswordOneActivity.this)
                    .setTitle("提醒")
                    .setMessage("请输入密码")
                            .setPositiveButton("确定", null)
                            .show();
            return;

        }

        String url = HttpUtil.getAbsoluteUrl("user/json/uppassword?uid=" + user.getId().toString() +"&password=" + password1);
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
                        Toast.makeText(PasswordOneActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
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
