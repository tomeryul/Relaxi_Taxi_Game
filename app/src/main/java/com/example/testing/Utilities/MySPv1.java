package com.example.testing.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class MySPv1 {

    private static final String DB_FILE = "DB_FILE";

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

}



