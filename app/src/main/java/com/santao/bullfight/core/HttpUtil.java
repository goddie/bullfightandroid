package com.santao.bullfight.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpUtil {

    public static final String BASE_URL = "http://app.santaotech.com:8080/";
    public static final String BASE_URL_APP  = "http://app.santaotech.com:8080/bullfight/";

//    public static final String BASE_URL = "http://192.168.0.116:8080/";
//    public static final String BASE_URL_APP  = "http://192.168.0.116:8080/bullfight/";


    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL_APP + relativeUrl;
    }



    public  static String getDate(long timestamp)
    {
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }


    public  static  Boolean isNullOrEmpty(Object obj)
    {
        if(obj==null)
        {
            return true;
        }

        if(obj.toString().equals(""))
        {
        return  true;
        }

        return false;
    }




}
