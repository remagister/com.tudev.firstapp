package com.tudev.firstapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tudev.firstapp.R;
import com.tudev.firstapp.app.ContactsApplication;
import com.tudev.firstapp.prefs.AppPreferences;
import com.tudev.firstapp.presenter.IPresenter;

/**
 * Created by arseniy on 07.08.16.
 */

public abstract class ViewBase<T extends IPresenter> extends AppCompatActivity implements IView {

    private T presenter;

    protected abstract T onPresenterCreate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppPreferences.INSTANCE.getTheme());
        presenter = onPresenterCreate();
        presenter.onCreate(this);
    }

    protected void initializePresenter(){
        presenter.initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ContactsApplication app = (ContactsApplication) getApplicationContext();

        switch (id){
            case R.id.action_default_theme:
                if(AppPreferences.INSTANCE.getTheme() != R.style.AppThemeColored){
                    app.switchTheme(AppPreferences.APP_THEME_DEFAULT);
                    finish();
                    startActivity(getIntent());
                }
                break;
            case R.id.action_alternate_theme:
                if(AppPreferences.INSTANCE.getTheme() != R.style.AppThemeDim) {
                    app.switchTheme(AppPreferences.APP_THEME_CUSTOM);
                    finish();
                    startActivity(getIntent());
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.onSaveState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        presenter.onLoadState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void message(int string_id) {
        Toast.makeText(this, string_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Intent createIntent(Class<?> activity) {
        return new Intent(this, activity);
    }

    @Override
    public void goToActivity(Intent intent) {
        startActivity(intent);
    }
}
