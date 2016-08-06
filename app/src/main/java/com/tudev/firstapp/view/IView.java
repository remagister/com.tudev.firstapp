package com.tudev.firstapp.view;

import android.content.Intent;

import com.tudev.firstapp.data.dao.Contact;

import java.util.List;

/**
 * Created by arseniy on 07.08.16.
 */

public interface IView {

    void goToActivity(Intent intent);

    void message(String message);

    Intent createIntent(Class<?> activity);

}
