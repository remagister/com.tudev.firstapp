package com.tudev.firstapp.adapter;

/**
 * Created by arseniy on 09.08.16.
 */

public interface IStateChangedListener<T extends Enum<T>>{
    void stateChanged(T newState);
}