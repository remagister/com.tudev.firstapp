package com.tudev.firstapp;

import android.content.Context;
import android.net.Uri;


/**
 * Created by arseniy on 17.08.16.
 */

public interface IEditImagePresenter {

    void onImageReceived(Context context, Uri imageUri);
}
