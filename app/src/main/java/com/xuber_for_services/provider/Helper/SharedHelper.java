package com.xuber_for_services.provider.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedHelper {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static final String PREFS_NAME = "JustDail";
    public static final String KYC_NAME = "KYC_NAME";
    public static final String KYC_ID = "KYC_ID";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";


    public static void putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.commit();

    }

    public static void setKycId(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("KYC_ID", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.commit();

    }
    public static Boolean getKycId(Context contextGetKey) {
        sharedPreferences = contextGetKey.getSharedPreferences("KYC_ID", Context.MODE_PRIVATE);
        Boolean Value = sharedPreferences.getBoolean(KYC_ID, false);
        Log.e("kyc_id" , ""+Value);
        return Value;
    }

    public static Boolean getKycName(Context contextGetKey) {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        Boolean Value = sharedPreferences.getBoolean(KYC_NAME, false);
        Log.e("default" , ""+Value);
        return Value;
    }

    public static void putBoo(Context context, Boolean kyc) {

        sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //editor.putString(Key, Value);
        editor.putBoolean(KYC_NAME, kyc);
        editor.commit();

    }
    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "");
        return Value;

    }

}
