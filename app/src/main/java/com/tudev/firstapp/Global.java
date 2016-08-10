package com.tudev.firstapp;

import android.util.SparseBooleanArray;

import com.tudev.firstapp.adapter.ContactAdapterState;

/**
 * Created by arseniy on 10.08.16.
 */

public enum Global {
    INSTANCE;

    private SparseBooleanArray store;

    private ContactAdapterState state = ContactAdapterState.NORMAL;

    public ContactAdapterState getState() {
        return state;
    }

    public void setState(ContactAdapterState state) {
        this.state = state;
    }

    public void store(SparseBooleanArray array){
        store = array;
    }

    public SparseBooleanArray getStore(){
        return store;
    }
}
