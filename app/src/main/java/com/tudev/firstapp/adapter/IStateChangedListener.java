package com.tudev.firstapp.adapter;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by arseniy on 09.08.16.
 */

public interface IStateChangedListener<T extends Enum<T>>{

    void onStateChanged(StateChangedEvent<T> event);
}