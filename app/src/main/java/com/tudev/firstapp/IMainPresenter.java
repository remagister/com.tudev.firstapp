package com.tudev.firstapp;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;

import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.presenter.IPresenter;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IMainPresenter extends IPresenter {

    void buttonClicked(AddButtonIntent state);
    void itemClicked(Contact.ContactSimple contactSimple);

    void initialize();
    void restore(Bundle saved);
    void saveInstanceState(Bundle bundle);

    void onItemLongClick();
    void onBackPressed();
}
