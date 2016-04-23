package com.santao.bullfight.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.santao.bullfight.R;
import com.santao.bullfight.core.BaseApplication;
import com.santao.bullfight.core.HttpUtil;
import com.santao.bullfight.core.MultipartRequest;
import com.santao.bullfight.core.Utils;
import com.santao.bullfight.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegThreeActivity extends BaseAppCompatActivity {

    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.txtPosition)
    TextView txtPosition;

    @Bind(R.id.txtCity)
    TextView txtCity;

    private String[] items = new String[] { "选择相册图片", "拍照" };
    private String[] positions = new String[] {"控球后卫", "得分后卫","小前锋","大前锋","中锋"};
    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()+"/";

    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;


    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_three);
        ButterKnife.bind(this);

        initTopBar();



        imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION+ UUID.randomUUID().toString()+".jpg"));
    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();
        setTitle("注册");
    }

    @OnClick({R.id.img1,R.id.txtSel})
    public void avatarClick()
    {
        showDialog();
    }

    @OnClick({R.id.llPosition})
    public void positionClick()
    {
        showPosition();
    }


    @OnClick({R.id.btn2})
    public  void skip()
    {
        regSuccess();
        finish();
    }

    @OnClick({R.id.btn1})
    public  void next()
    {
        User user = baseApplication.getLoginUser();

        String position = txtPosition.getText().toString();
        String city = txtCity.getText().toString();
        String uid = user.getId().toString();

//        if(user==null || HttpUtil.isNullOrEmpty(position) || HttpUtil.isNullOrEmpty(city) || HttpUtil.isNullOrEmpty(uid))
//        {
//
//            new  AlertDialog.Builder(RegThreeActivity.this)
//                    .setTitle("提醒")
//                    .setMessage("请输入个人信息" )
//                    .setPositiveButton("确定" ,  null )
//                    .show();
//            return;
//
//        }

        String url = HttpUtil.getAbsoluteUrl("user/json/regthree?position="+position+"&city=" + city+"&uid="+uid);
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


    private  void regSuccess()
    {
        Toast.makeText(RegThreeActivity.this, getString(R.string.reg_success), Toast.LENGTH_SHORT).show();
    }

    private void showPosition() {

        new AlertDialog.Builder(this)
                .setTitle("场上位置")
                .setItems(positions, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtPosition.setText(positions[which]);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }



    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
                                break;
                            case 1:

                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (Utils.hasSdcard()) {

                                    String path = Utils.getPathByUri4kitkat(RegThreeActivity.this, imageUri);
                                    File tmp = new File(path);

                                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tmp));
                                }

                                startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED)
        {
            return;
        }

        switch (requestCode) {
            case IMAGE_REQUEST_CODE:


                Uri uri = data.getData();

                startPhotoZoom(uri);

                //String path = Utils.getPathByUri4kitkat(this, uri);
                //tempFile = new File(path);

                break;
            case CAMERA_REQUEST_CODE:
                if (Utils.hasSdcard()) {
                    //tempFile = new File(Environment.getExternalStorageDirectory()+ IMAGE_FILE_NAME);
                    startPhotoZoom(imageUri);
                } else {
                    Toast.makeText(RegThreeActivity.this, R.string.no_card,
                            Toast.LENGTH_LONG).show();
                }

                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);

        //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //intent.putExtra("noFaceDetection", true); // no face detection


        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            img1.setImageDrawable(drawable);

            String path = Utils.getPathByUri4kitkat(this, imageUri);

            File tmp = new File(path);



            FileOutputStream fOut = null;
            try {
                tmp.createNewFile();
                fOut = new FileOutputStream(tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            photo.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }




            if(tmp!=null&&tmp.exists())
            {
                upload(tmp);
            }


        }
    }

    void upload(File file)
    {
        User user = baseApplication.getLoginUser();
        String url = HttpUtil.getAbsoluteUrl("user/json/upavatar");
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("uid", user.getId().toString());

        MultipartRequest multipartRequest = new MultipartRequest(url,"file",file,params,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegThreeActivity.this, R.string.action_success,
                                Toast.LENGTH_LONG).show();

                        String path = Utils.getPathByUri4kitkat(RegThreeActivity.this, imageUri);

                        File tmp = new File(path);

                        tmp.delete();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                    }
                }
        );

        try {
            Log.d("", multipartRequest.getBody().toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }


        BaseApplication.getHttpQueue().add(multipartRequest);



    }




}
