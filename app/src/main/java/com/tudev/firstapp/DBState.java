package com.tudev.firstapp;


/**
 * Created by Саша on 04.08.2016.
 */
public enum DBState {
    INSTANCE;

    private ContactDBState state = ContactDBState.INTACT;

    public void setState(ContactDBState state) {
        this.state = state;
    }

    public ContactDBState getState() { return state; }

    public void reset(){
        state = ContactDBState.INTACT.with(ContactDBState.NO_ID);
    }
}
