package com.tudev.firstapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by arseniy on 07.08.16.
 */

public abstract class ViewBase extends AppCompatActivity implements IView {

    @Override
    public void message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
