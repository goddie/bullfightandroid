package com.santao.bullfight.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.activity.LoginActivity;
import com.santao.bullfight.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by goddie on 16/3/8.
 */
public class BaseApplication extends Application {

    private static RequestQueue requestQueue;

    private static Context mContext;

    private static final String TAG = "BaseApplication";

    private User loginUser;


    private static BaseApplication baseApplication = null;

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        requestQueue= Volley.newRequestQueue(getApplicationContext());


        mContext=getApplicationContext();

        baseApplication = this;





//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        PlatformConfig.setWeixin("wx2e6d0b15a83c3d77", "d4624c36b6795d1d99dcf0547af5443d");
//        //微信 appid appsecret
//        PlatformConfig.setSinaWeibo("4249415536", "8ef2bddf78e75fdeed74898108499dab");
//        //新浪微博 appkey appsecret
//        PlatformConfig.setQQZone("1104986282", "rIBfhDCmNAmAmCq2");
        // QQ和Qzone appid appkey
    }

    public static RequestQueue getHttpQueue(){
        return requestQueue;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public static Context getContext(){
        return mContext;
    }



    public  User getLoginUser()
    {

        if(loginUser==null)
        {

            User user = Utils.getLocalUser(getApplicationContext());
            if(user!=null)
            {
                reload();

                this.loginUser = user;
                return this.loginUser;
            }


            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(intent);
            return  null;
        }
        return this.loginUser;
    }


    public  Boolean isLogin()
    {
        User user = Utils.getLocalUser(getApplicationContext());

        if(user!=null)
        {
            return true;
        }

        return false;
    }


    public  void setLoginUser(User user)
    {
        this.loginUser = user;
    }


    private void reload()
    {

        User user = Utils.getLocalUser(getApplicationContext());

        String url = HttpUtil.getAbsoluteUrl("user/json/login?username=" + user.getUsername()+"&password="+user.getPassword());

        Log.d("login", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int code = jsonObject.getInt("code");

                    if(code==1)
                    {

                        JSONObject obj = jsonObject.getJSONObject("data");


                        User entity = gson.fromJson(obj.toString(), User.class);

                        baseApplication.setLoginUser(entity);

                        Utils.saveLocalUser(getContext(),entity);
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
