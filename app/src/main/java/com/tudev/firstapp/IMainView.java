package com.tudev.firstapp;

import android.util.SparseBooleanArray;

import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.view.IView;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IMainView extends IView {

    void setContactsList(List<Contact.ContactSimple> contacts);

    List<Contact.ContactSimple> getRemoveList();

    void initState(ContactAdapterState state);

    void notifyDataChanged();

}
