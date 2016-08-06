package com.tudev.firstapp;

import android.content.Context;
import android.view.View;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.presenter.IPresenter;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IMainPresenter extends IPresenter {

    void buttonClicked(AddButtonIntent state);
    void itemClicked(Contact.ContactSimple contactSimple);
}
