package com.example.swaggertodo.api.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    public static final String TOKEN_KEY = "TOKEN";
    public static final String ISLOGIN_KEY = "LOGIN";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //untuk menyimpan data ke shared preferences
    public void setToken(String value){
        editor.putString(TOKEN_KEY, value);
        editor.apply();
    }
    public void setIsLogin(Boolean value){
        editor.putBoolean(ISLOGIN_KEY, value);
        editor.apply();
    }

    //untuk mengambil data dari shared preferences
    public String getToken(){
        return sharedPreferences.getString(TOKEN_KEY, null);
    }
    public boolean IsLogin(){
        return sharedPreferences.getBoolean(ISLOGIN_KEY, false);

    }
}
