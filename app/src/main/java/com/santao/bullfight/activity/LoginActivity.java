package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.R;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseAppCompatActivity {


    @Bind(R.id.btn2)
    Button btn2;

    @Bind(R.id.txtUsername)
    TextView txtUsername;

    @Bind(R.id.txtPassword)
    TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initTopBar();
    }



    @OnClick({ R.id.btn2 })
    public void onClick(View v) {

        login(txtUsername.getText().toString(), txtPassword.getText().toString());

        //Log.d("", "btn2 click");
    }


    @OnClick({ R.id.txt1 })
    public void onTxt1Click(View v) {

        Log.d("","txt1 click");
    }

    @OnClick({ R.id.txt2 })
    public void onTxt2Click(View v) {

        Intent intent = new Intent(LoginActivity.this,RegActivity.class);
        startActivity(intent);
        finish();
        //Log.d("","txt2 click");
    }


    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("登录");
    }

    private void login(String username,String password)
    {
        String url = HttpUtil.getAbsoluteUrl("user/json/login?username=" + username+"&password="+password);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = jsonObject.getInt("code");

                    if(code==1)
                    {

                        JSONObject obj = jsonObject.getJSONObject("data");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        Article entity = gson.fromJson(jsonArray.get(i).toString(), Article.class);
//                        list.add(entity);
//                    }

                        User entity = gson.fromJson(obj.toString(), User.class);

                        baseApplication.setLoginUser(entity);

                        Utils.saveLocalUser(LoginActivity.this,entity);

                        finish();


                    }else
                    {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
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
