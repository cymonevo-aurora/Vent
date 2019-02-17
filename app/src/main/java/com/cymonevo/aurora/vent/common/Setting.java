package com.cymonevo.aurora.vent.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.cymonevo.aurora.vent.constant.SharedPreference;

public abstract class Setting {

    public static int getCurrentTheme(Context context) {
        SharedPreferences setting = context.getSharedPreferences(SharedPreference.SETTING_THEME.toString(), Context.MODE_PRIVATE);
        return setting.getInt(SharedPreference.THEME_CURRENT.toString(), 0);
    }

    public static void setCurrentTheme(Context context, int value) {
        SharedPreferences.Editor setting = context.getSharedPreferences(SharedPreference.SETTING_THEME.toString(), Context.MODE_PRIVATE).edit();
        setting.putInt(SharedPreference.THEME_CURRENT.toString(), value);
        setting.apply();
    }
}
