package com.tudev.firstapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.EnumSet;


/**
 * Created by arseniy on 09.08.16.
 */

public abstract class StateAdapter<T extends Enum<T>> extends BaseAdapter implements IStateChangedListener<T>{

    private T state;

    private LayoutInflater internalInflater;

    public StateAdapter(T initial){
        this(initial, null);
    }

    public StateAdapter(T initial, LayoutInflater inflater){
        internalInflater = inflater;
        this.state = initial;
    }

    protected LayoutInflater getInternalInflater() {
        return internalInflater;
    }

    public void setInternalInflater(LayoutInflater internalInflater) {
        this.internalInflater = internalInflater;
    }


    protected abstract View getViewState(int i, View view, ViewGroup group, T state);

    @Override
    public final View getView(int i, View view, ViewGroup viewGroup) {
        return getViewState(i, view, viewGroup, state);
    }

    public T getState() {
        return state;
    }

    @Override
    public void stateChanged(T newState) {
        state = newState;
        super.notifyDataSetInvalidated();
    }
}
