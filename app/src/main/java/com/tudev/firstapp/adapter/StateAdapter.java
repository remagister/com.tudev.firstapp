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
    private T initial;

    private static final int MAX_SIZE = 16;  // 2 int doubled
    private static final int CACHE_CODE = 0xDEAD;
    private LruCache<Integer, T> cache;

    private LayoutInflater internalInflater;

    public StateAdapter(T initial){
        this(initial, null);
    }

    public StateAdapter(T initial, LayoutInflater inflater){
        internalInflater = inflater;
        this.initial = initial;
        cache = new LruCache<>(MAX_SIZE);
        state = cache.get(CACHE_CODE);
        if (state == null) {
            state = initial;
        }
    }

    protected LayoutInflater getInternalInflater() {
        return internalInflater;
    }

    public void setInternalInflater(LayoutInflater internalInflater) {
        this.internalInflater = internalInflater;
    }

    public void onDestroy(){
        cache.put(CACHE_CODE, state);
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
    }
}
