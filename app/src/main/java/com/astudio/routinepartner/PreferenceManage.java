package com.astudio.routinepartner;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManage {

    private static final String PREFERENCE_NAME="rebuild_preference";
    private static final String DEFAULT_VALUE_STRING = "";
    private static final float DEFAULT_VALUE_FLOAT=0;

    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value){
        SharedPreferences sharedPreferences=getPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context,String key){
        SharedPreferences sharedPreferences=getPreferences(context);
        String value=sharedPreferences.getString(key,DEFAULT_VALUE_STRING);
        return value;
    }

    public static void setFloat(Context context, String key, float value) {
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float getFloat(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        float value = prefs.getFloat(key, DEFAULT_VALUE_FLOAT);
        return value;
    }

    public static void removeKey(Context context, String key) {

        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context) {

        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

}