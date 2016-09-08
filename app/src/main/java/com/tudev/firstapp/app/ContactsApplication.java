package com.tudev.firstapp.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.tudev.firstapp.R;
import com.tudev.firstapp.prefs.AppPreferences;

/**
 * Created by arseniy on 08.09.16.
 */

public class ContactsApplication extends Application {

    private static final String PREFERENCES = "shared";
    private static final String FIRST_ASSIGNATION = "fa";
    public static final String THEME_KEY = "theme";

    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        if(!preferences.contains(FIRST_ASSIGNATION)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(THEME_KEY, AppPreferences.APP_THEME_DEFAULT);
            editor.putBoolean(FIRST_ASSIGNATION, false);
            editor.apply();
        } else {
            int theme = preferences.getInt(THEME_KEY, AppPreferences.APP_THEME_DEFAULT);
            _switchTheme(theme);
        }

    }

    private void _switchTheme(int theme){
        switch (theme){
            case AppPreferences.APP_THEME_DEFAULT:
                AppPreferences.INSTANCE.setTheme(R.style.AppThemeColored);
                break;
            case AppPreferences.APP_THEME_CUSTOM:
                AppPreferences.INSTANCE.setTheme(R.style.AppThemeDim);
                break;
        }
    }

    public void switchTheme(int theme){
        _switchTheme(theme);
        preferences.edit().putInt(THEME_KEY, theme).apply();
    }
}
