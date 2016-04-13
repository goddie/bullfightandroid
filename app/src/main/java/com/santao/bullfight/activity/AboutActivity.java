package com.santao.bullfight.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.santao.bullfight.R;
import com.santao.bullfight.core.HttpUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseAppCompatActivity {


    @Bind(R.id.webView)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initTopBar();



        String url  = HttpUtil.getAbsoluteUrl("article/page/detail?title=关于来斗牛");
        webView.loadUrl(url);
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("关于来斗牛");
    }
}
