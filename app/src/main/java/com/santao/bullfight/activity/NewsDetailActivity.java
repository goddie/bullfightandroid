package com.santao.bullfight.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseAppCompatActivity {

    @Bind(R.id.webView)
    WebView webView;

    private  String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);

        initTopBar();

        Bundle bud = getIntent().getExtras();
        if (bud != null && bud.containsKey("id")) {
            id = bud.getString("id");
        }


        String url  = HttpUtil.getAbsoluteUrl("article/page/newsdetail?uuid="+id);
        webView.loadUrl(url);

    }



}
