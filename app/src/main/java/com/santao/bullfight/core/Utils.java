package com.santao.bullfight.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;

import com.santao.bullfight.model.User;

/**
 * Created by goddie on 16/3/8.
 */
public class Utils {

    public static int screenWidth = 0;
    private static int screenHeight = 0;


    public static final String APP_UPDATE_SERVER_URL = "/update/update";
    public static final String INTELLIGENT_PICTURE_MODE = "0";
    public static final String NO_PICTURE_MODE = "1";
    public static final String SD_PICTURE_MODE = "2";
    public static final String HD_PICTURE_MODE = "3";


    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }


    /**
     * 检查是否存在SDCard
     * @return
     */
    public static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }


    public  static void saveLocalUser(Context context,User user)
    {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());

        //提交当前数据
        editor.commit();

    }


    public  static  User getLocalUser(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String username =sharedPreferences.getString("username", "");
        String password =sharedPreferences.getString("password", "");

        User user = new User();

        if(!HttpUtil.isNullOrEmpty(username))
        {
            user.setUsername(username);
        }

        if(!HttpUtil.isNullOrEmpty(password))
        {
            user.setPassword(password);
        }

        return user;
    }


}
