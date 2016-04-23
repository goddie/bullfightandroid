package com.santao.bullfight.activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.santao.bullfight.model.Team;
import com.santao.bullfight.model.User;
import com.santao.bullfight.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTeamEditActivity extends BaseAppCompatActivity {

    @Bind(R.id.txt1)
    EditText txt1;

    @Bind(R.id.txt2)
    EditText txt2;

    @Bind(R.id.txt3)
    TextView txt3;

    @Bind(R.id.txt4)
    EditText txt4;

    @Bind(R.id.img1)
    ImageView img1;


    private User user;
    private String[] items = new String[] { "选择相册图片", "拍照" };
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;



    private String avatar;

    private Team team;


    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    private static final String IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()+"//"+IMAGE_FILE_NAME;

    private Uri imageUri = Uri.fromFile(new File(IMAGE_FILE_LOCATION+ UUID.randomUUID().toString()+".jpg"));

    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_edit);

        ButterKnife.bind(this);
        initTopBar();

        user = baseApplication.getLoginUser();

        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        team = (Team)bundle.getSerializable("team");

        bind();

    }

    @Override
    public void onTopFinish() {
        super.onTopFinish();

        setTxtRight("保存");
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();

        save();

        //finish();
    }


    @OnClick({R.id.img1,R.id.btn1})
    public void avatar()
    {
        showDialog();
    }

    @OnClick({R.id.txt3})
    public void birthday()
    {
        selDate();
    }


    void selDate()
    {


        DatePickerDialog dateDlg = new DatePickerDialog(MyTeamEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();

                calendar.set(year,monthOfYear,dayOfMonth);

                Date date = new Date(calendar.getTimeInMillis());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String s = sdf.format(date);

                txt3.setText(s);

                Toast.makeText(MyTeamEditActivity.this, "确定日期: " + s, Toast.LENGTH_SHORT).show();


            }
        },2016,1,1);

        dateDlg.setCanceledOnTouchOutside(true);

        dateDlg.show();

    }

    void save()
    {


        StringBuilder sb = new StringBuilder();
        sb.append("&name=" + txt1.getText());
        sb.append("&city=" + txt2.getText());
        sb.append("&found=" + txt3.getText());
        sb.append("&info=" + txt4.getText());
        //sb.append("&avatar=" + avatar);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUtil.getAbsoluteUrl("team/json/update?tid=" + team.getId().toString() + sb.toString()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ArrayList<Object> list = new ArrayList<Object>();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int rs = jsonObject.getInt("code");

                    JSONObject obj = jsonObject.getJSONObject("data");

                    if(rs==1)
                    {

                        team = gson.fromJson(obj.toString(),Team.class);

                        if(tempFile!=null)
                        {
                            upload(tempFile);

                        }else
                        {
                            Toast.makeText(MyTeamEditActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                            finish();
                        }




                    }else
                    {
                        Toast.makeText(MyTeamEditActivity.this, getString(R.string.action_fail), Toast.LENGTH_SHORT).show();
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

                                    String path = Utils.getPathByUri4kitkat(MyTeamEditActivity.this, imageUri);
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
                    Toast.makeText(MyTeamEditActivity.this, R.string.no_card,
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
        String url = HttpUtil.getAbsoluteUrl("team/json/upavatar");
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("tid", team.getId().toString());

        MultipartRequest multipartRequest = new MultipartRequest(url,"file",file,params,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MyTeamEditActivity.this, getString(R.string.action_success), Toast.LENGTH_SHORT).show();
                        //finish();

                        String path = Utils.getPathByUri4kitkat(MyTeamEditActivity.this, imageUri);

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
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    void bind()
    {
        txt1.setText(team.getName());
        txt2.setText(team.getCity());
        txt3.setText(HttpUtil.getDate(team.getCreatedDate()));
        txt4.setText(team.getInfo());

        if(team.getAvatar()!=null)
        {
            Picasso.with(this).load(HttpUtil.BASE_URL + team.getAvatar()).transform(new CircleTransform()).placeholder(R.mipmap.holder)
                    .into(img1);

        }else
        {
            Picasso.with(this).load(R.mipmap.holder).transform(new CircleTransform())
                    .into(img1);
        }

    }
}
