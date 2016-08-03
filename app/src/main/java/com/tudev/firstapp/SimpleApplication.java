package com.tudev.firstapp;

import android.app.Application;

/**
 * Created by Саша on 03.08.2016.
 */
public class SimpleApplication extends Application {

    private ContactDBState state ;

    public SimpleApplication(){

    }

    public void setState(ContactDBState state) {
        this.state = state;
    }

    public ContactDBState getState() { return state; }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
