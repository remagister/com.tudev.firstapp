package com.tudev.firstapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tudev.firstapp.presenter.IPresenter;

/**
 * Created by arseniy on 07.08.16.
 */

public abstract class ViewBase<T extends IPresenter> extends AppCompatActivity implements IView {

    private T presenter;
    public abstract T onPresenterCreate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onPresenterCreate();
        presenter.onCreate(this);
    }

    protected void initializePresenter(){
        presenter.initialize();
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
