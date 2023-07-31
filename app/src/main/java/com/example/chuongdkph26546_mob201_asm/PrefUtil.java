package com.example.chuongdkph26546_mob201_asm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;




public class PrefUtil {
    private static final String K_IS_SIGNING = "is_purchased";


    private static volatile PrefUtil instance;
    private SharedPreferences appPref;

    private PrefUtil(Context context) {
        this.appPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PrefUtil get(Context context) {
        if (instance == null) {
            synchronized (PrefUtil.class) {
                if (instance == null) {
                    instance = new PrefUtil(context);
                }
            }
        }
        return instance;
    }


    public boolean isSigning() {
        return appPref.getBoolean(K_IS_SIGNING, false);
    }


    public void setSigning(boolean value) {
        this.appPref.edit().putBoolean(K_IS_SIGNING, value).apply();
    }

   }