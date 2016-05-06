package com.santao.bullfight.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseAppCompatActivity {

    @Bind(R.id.webView)
    WebView webView;

    private  String id;

    private String imgurl;
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

        registerForContextMenu(webView);
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTxtRight("评论");
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();

        Intent intent = new Intent(NewsDetailActivity.this,CommetListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("aid",id);

        intent.putExtras(bundle);

        startActivity(intent);

    }

    /***
     * 功能：长按图片保存到手机
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle() == "保存小图到手机") {
                    new SaveImage(0).execute(); // Android 4.0以后要使用线程来访问网络
                } else {
                    return false;
                }
                return true;
            }
        };


        MenuItem.OnMenuItemClickListener handler2 = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle() == "保存原图到手机") {
                    new SaveImage(1).execute(); // Android 4.0以后要使用线程来访问网络
                } else {
                    return false;
                }
                return true;
            }
        };



        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            if (result != null) {
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    imgurl = result.getExtra();
                    menu.setHeaderTitle("提示");
                    menu.add(0, v.getId(), 0, "保存小图到手机").setOnMenuItemClickListener(handler);
                    menu.add(0, v.getId(), 0, "保存原图到手机").setOnMenuItemClickListener(handler2);
                }
            }
        }
    }

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     *
     */
    private class SaveImage extends AsyncTask<String, Void, String> {

        private int type = 0;

        public  SaveImage(int type)
        {
            this.type = type;
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }




                int idx = imgurl.lastIndexOf(".");
                String ext = imgurl.substring(idx);

                String pref = "";
                if(type==1)
                {
                    imgurl = imgurl.substring(0,imgurl.indexOf("_"))+ext;
                }

                file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(imgurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            //MsgBox("提示", result);

            Toast.makeText(NewsDetailActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }




}
