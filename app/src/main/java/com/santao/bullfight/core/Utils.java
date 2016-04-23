package com.santao.bullfight.core;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
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

        Gson gson=new Gson();
        String s= gson.toJson(user);

        editor.putString("userJson", s);

        //提交当前数据
        editor.commit();

    }


    public  static  User getLocalUser(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences("user",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String s =sharedPreferences.getString("userJson", "");

        Gson gson=new Gson();
        User user = new User();

        if(!HttpUtil.isNullOrEmpty(s))
        {
            user = (User)gson.fromJson(s,User.class);
            return user;
        }


        return null;
    }


    public  static void clearLocalUser(Context context)
    {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据

        editor.putString("userJson", null);

        //提交当前数据
        editor.commit();

    }



//    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
//                        Long.valueOf(id));
//                return getDataColumn(context, contentUri, null, null);
//            } else if (isMediaDocument(uri)) {// MediaProvider
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[] { split[1] };
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
//            // (and
//            // general)
//            return getDataColumn(context, uri, null, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
//            return uri.getPath();
//        }
//        return null;
//    }


    public static String getPathByUri4kitkat( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals(scheme) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals(scheme) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
