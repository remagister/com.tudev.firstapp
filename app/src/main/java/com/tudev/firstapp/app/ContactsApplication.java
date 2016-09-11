package com.tudev.firstapp.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.tudev.firstapp.R;
import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.SQLiteHelperBuilder;
import com.tudev.firstapp.prefs.AppPreferences;

/**
 * Created by arseniy on 08.09.16.
 */

public class ContactsApplication extends Application {

    private static ContactsApplication applicationInstance;
    private static final String PREFERENCES = "shared";
    private static final String FIRST_ASSIGNATION = "fa";
    public static final String THEME_KEY = "theme";

    private SharedPreferences preferences;

    public static ContactsApplication getApplicationInstance(){
        return applicationInstance;
    }

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

        applicationInstance = this;
    }

    public IContactDAO openContactDao(){
        return new ContactDAO(new SQLiteHelperBuilder(this));
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
