package com.tudev.firstapp.adapter;

/**
 * Created by arseniy on 23.08.16.
 */

public class StateChangedEvent<T extends Enum<T>> {
    private T state;

    public StateChangedEvent(T state) {
        this.state = state;
    }

    public T getState(){
        return state;
    }
}
