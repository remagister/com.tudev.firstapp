package com.tudev.firstapp.prefs;

import android.content.SharedPreferences;

/**
 * Created by arseniy on 08.09.16.
 */

public enum AppPreferences {
    INSTANCE;

    public static final int APP_THEME_DEFAULT = 1;
    public static final int APP_THEME_CUSTOM = 2;

    private int theme = APP_THEME_DEFAULT;

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }
}
