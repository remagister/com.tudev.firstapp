package com.tudev.firstapp;

/**
 * Created by Саша on 03.08.2016.
 */
public enum ContactDBState {
    INTACT,
    MODIFIED;

    public static final long NO_ID = -1;

    private long item_id = -1;

    public long getId() {
        return item_id;
    }

    public boolean hasId(){
        return item_id != -1;
    }

    public ContactDBState with(long id){
        item_id = id;
        return this;
    }
}
