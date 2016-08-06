package com.tudev.firstapp.presenter;

import android.content.Context;

/**
 * Created by arseniy on 07.08.16.
 */

public interface IPresenter {
    void onCreate(Context context);
    void onPause();
    void onResume();
    void onDestroy();
}
