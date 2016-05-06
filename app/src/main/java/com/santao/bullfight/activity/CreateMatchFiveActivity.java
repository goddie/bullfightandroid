package com.santao.bullfight.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.alipay.PayResult;
import com.santao.bullfight.alipay.SignUtils;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.model.MatchFight;
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateMatchFiveActivity extends BaseAppCompatActivity {

    @Bind(R.id.imgNext)
    Button imgNext;

    @Bind(R.id.txt11)
    TextView txt11;

    @Bind(R.id.txt21)
    TextView txt21;

    @Bind(R.id.txt31)
    TextView txt31;


    @Bind(R.id.txt1)
    TextView txt1;

    @Bind(R.id.txt2)
    TextView txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.txt4)
    TextView txt4;

    private MatchFight matchFight;

    private double arenaPay=0;
    private double judgePay=0;
    private double dataRecordPay=0;
    private double totalPay = 0;

    private JSONObject obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match_five);

        ButterKnife.bind(this);

        initTopBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey("matchFight")) {
            matchFight = (MatchFight)bundle.getSerializable("matchFight");
        }


        getFee();


    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("创建比赛");


    }


    @OnClick({R.id.imgNext})
    public void btnClick()
    {
        if(matchFight.getFee()==0)
        {
            createMatch();
        }else
        {
            addOrder();
        }

    }

    void createMatch()
    {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        User user = baseApplication.getLoginUser();


        String startStr = "";
        try {
            startStr = URLEncoder.encode(sdf.format(new Date(matchFight.getStart())),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String endStr = "";
        try {
            endStr = URLEncoder.encode(sdf.format(new Date(matchFight.getStart())),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder sb =new StringBuilder();
        sb.append("?uid="+user.getId().toString());
        sb.append("&tid="+ matchFight.getHost().getId().toString());
        sb.append("&aid="+matchFight.getArena().getId().toString());
        sb.append("&matchType="+matchFight.getMatchType());
        sb.append("&status=0");
        sb.append("&startStr="+ startStr);
        sb.append("&endStr="+ endStr);
        sb.append("&guestScore=0");
        sb.append("&hostScore=0");
        sb.append("&teamSize="+matchFight.getTeamSize());
        sb.append("&judge="+matchFight.getJudge());
        sb.append("&dataRecord="+matchFight.getDataRecord());
        sb.append("&isPay="+matchFight.getIsPay());
        sb.append("&fee="+matchFight.getFee());
//        sb.append("&fee=0.01");
        sb.append("&content="+matchFight.getContent());

        try {
            sb.append("&content="+ URLEncoder.encode(matchFight.getContent(), "UTF-8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        String url = HttpUtil.getAbsoluteUrl("matchfight/json/add"+sb.toString());

        final Team[] team = {null};

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

                        MatchFight entity = gson.fromJson(obj.toString(), MatchFight.class);

                        matchFight = entity;

                        if(matchFight.getFee()==0)
                        {
                            Toast.makeText(CreateMatchFiveActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                            finish();
                        }


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



    //获取收费标准
    void getFee()
    {
        String url = HttpUtil.getAbsoluteUrl("arena/json/getPrice?aid="+matchFight.getArena().getId());



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

                        JSONArray array = jsonObject.getJSONArray("data");

                        arenaPay = array.getDouble(0);
                        judgePay = array.getDouble(1);
                        dataRecordPay = array.getDouble(2);


                        updateTable();
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


    void updateTable()
    {
        DecimalFormat df = new DecimalFormat("0.0");

        txt11.setText(matchFight.getTeamSize()+ "vs"+matchFight.getTeamSize()+"比赛场地费用");
        txt21.setText(matchFight.getTeamSize()+ "vs"+matchFight.getTeamSize()+"比赛裁判费用");
        txt31.setText(matchFight.getTeamSize() + "vs" + matchFight.getTeamSize() + "比赛数据员费用");

        //比赛场地费
        double count = Math.ceil((matchFight.getEnd() - matchFight.getStart())/(60*60*1000));
        double t = count * arenaPay * 0.5f;

        txt1.setText(df.format(t));

        //裁判
        double t2 = matchFight.getJudge() * judgePay * 0.5f;
        txt2.setText(df.format(t2));


        //数据员

        double t3 = matchFight.getDataRecord()*dataRecordPay*0.5f;
        txt3.setText(df.format(t3));


        totalPay = t+t2+t3;


        txt4.setText(df.format(totalPay));

        matchFight.setFee((float) totalPay);

        if(matchFight.getFee()==0)
        {
            imgNext.setText("免费创建比赛");
        }else
        {
            createMatch();
        }

    }

    //创建订单
    void addOrder()
    {

        User user = baseApplication.getLoginUser();

        String name = "";
        try {
            name = URLEncoder.encode(matchFight.getArena().getName(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url = HttpUtil.getAbsoluteUrl("order/json/add?uid="+user.getId()+"&mfid="+matchFight.getId()
                +"&name="+name+"&total="+totalPay+"&payType=1");

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

                        obj = jsonObject.getJSONObject("data");

                       // pay(obj.getString("name"),String.valueOf(totalPay));
                        pay(obj.getString("name"),"0.01");
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


    //主场支付
    void payHost()
    {
        User user = baseApplication.getLoginUser();

        String url = HttpUtil.getAbsoluteUrl("payrecord/json/payhost?uid="+user.getId()+"&mfid="+matchFight.getId());

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
                        Toast.makeText(CreateMatchFiveActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
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




    // 商户PID
    public static final String PARTNER = "2088911907194201";
    // 商户收款账号
    public static final String SELLER = "bj_santao@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALz/UeE0KA7eCZXqWxIfJV+V+zif/4qvUCGvw6XibyZvpVQpEUMclaKGKX2ih7kkqjNZv5yt/Txoyi37BVgTq3kUl9zBLplSElrWp8u631S9UZUtnRD2Mc4dpbdvOWVWi3Kn4GooxMnT9C4g1ILZcRtIpKpx2iH4cbepIjigxAoXAgMBAAECgYEAu/NU5BbQN2jMM5AqHS1oJ1SpzrgekzahA78dXByA2MJyse1dQ1Zr4IJ3RH+bZZ12vTZlfVTx319+oJdfyyVUgY+rXdYu0MLdVRzqPBVVEjWTTwScwyTSjehlIMT5Gprf08PzfzeADwBwP3+oihOKsCP42HK23z/UhsZKj2/uwYECQQDwRx7geCVA/sByTXnCdPgFvxeHOccukoVRUuJYr4xZXlRemOxUwyIInsig44hehrbJs+a9xpI848QTpTone81hAkEAyV0z/i96QibntW+hGr/B7gwA4gnbkC8xkIU7MNEiNr4WpLF+jglwTFyZv+to3u8PafgfsJdQEtWYSZJO4n7SdwJAOvu6gK/9tS7UXzrVoP7Fw+NdCz0LwEsHnycRmWO+uFGHtJElsskUGbmg1p4EY+/9/xXCluOgEoJ3J7tvwzGJAQJAO6QKaUgAqyVAzeFxUy3mr64IeOq4iH0h7g84F95phtNIe6FCvakYBNYMh+ae2iDubNGb+T7n7ZwsDeZyzO0JQwJBAJJLWKFqNP+j1hXdpY+o4HOh5oTGqa8li5CTszDjE/dSepyYn1QbSOBdtCN6tvNsLahntw/SZfT20ffVzey4434=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(CreateMatchFiveActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        payHost();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(CreateMatchFiveActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(CreateMatchFiveActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(String name,String total) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo(name, "比赛费用支付", total);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(CreateMatchFiveActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
//    public void h5Pay(View v) {
//        Intent intent = new Intent(this, H5PayDemoActivity.class);
//        Bundle extras = new Bundle();
//        /**
//         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
//         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
//         * 商户可以根据自己的需求来实现
//         */
//        String url = "http://m.meituan.com";
//        // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
//        extras.putString("url", url);
//        intent.putExtras(extras);
//        startActivity(intent);
//
//    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {

        String key = null;
        try {
            key = obj.getString("tradeNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
