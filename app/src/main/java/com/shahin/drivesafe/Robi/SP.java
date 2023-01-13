package com.shahin.drivesafe.Robi;

import android.content.Context;
import android.content.SharedPreferences;

import com.shahin.drivesafe.MyApp;


/**
 * Created by Atiar on 5/23/18.
 */

public class SP {



    private static final String PREFS_NAME = "preferenceName";


    private static final String fcm_token = "fcmToken";
    private static final String sub_code = "subcode";


    private static final String ad_stat = "adstatus";
    private static final String sub_stat = "substatus";
    private static final String sub_click = "subclick";

    private static final String highScore = "highScore";



    public static boolean setPreference(String key, String value) {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(String key) {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "none");
    }



    public static boolean setPreferenceBool(String key, Boolean value) {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    public static boolean getPreferenceBool(String key) {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, false);
    }



    public static void setSubscriptionStatus(Boolean status){
        setPreferenceBool(sub_stat,status);

    }

    public static Boolean getSubscriptionStatus(){
        return getPreferenceBool(sub_stat);
    }


    public static void setSubscriptionClicked(Boolean status){
        setPreferenceBool(sub_click,status);

    }



    public static void setSubCode(String sub){
        setPreference(sub_code,sub);

    }




    /*****************************//* End shared preferences *//******************************/


}
