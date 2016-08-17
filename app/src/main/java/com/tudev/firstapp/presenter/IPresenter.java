package com.tudev.firstapp.presenter;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by arseniy on 07.08.16.
 */

public interface IPresenter {
    void onCreate(Context context);
    void onStop();
    void onStart();
    void onDestroy();

    void onResume();
    void initialize();

    void onSaveState(Bundle bundle);
    void onLoadState(Bundle bundle);
}
