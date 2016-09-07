package com.tudev.firstapp.calendarpicker.container;

import android.widget.Scroller;

import com.tudev.firstapp.calendarpicker.drawable.IDrawable;
import com.tudev.firstapp.calendarpicker.drawable.IMeasurable;
import com.tudev.firstapp.calendarpicker.drawable.Item;
import com.tudev.firstapp.calendarpicker.provider.IDataWindow;

/**
 * Created by arseniy on 30.08.16.
 */

public interface IContainer extends IMeasurable, Iterable<IDrawable>{

    void setDataWindow(IDataWindow window);
    void scroll(float dx);
    void fling(float dy, Scroller scroller);
    void setMaxItemSize(float width, float height);
    Item getCurrent();
    float getCentralUpperBound();
    void refresh();
    void setOrigin(float x, float y);
    void setOnItemChangedListener(OnItemChangedListener listener);
    boolean hit(float x, float y);
    void setVisibleItems(int visibleItems);
}
