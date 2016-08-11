package com.tudev.firstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.SQLiteHelperBuilder;
import com.tudev.firstapp.presenter.ContactPresenterBase;

/**
 * Created by arseniy on 07.08.16.
 */

public class EditPresenter extends ContactPresenterBase<IEditView> implements IEditPresenter {

    private ContactActionIntent editingIntent;
    private Contact contact;

    public EditPresenter(IEditView parentView) {
        super(parentView, true);
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        Intent intent = ((Activity) context ).getIntent();
        editingIntent = (ContactActionIntent) intent
                .getSerializableExtra(MainPresenter.CONTACT_EDIT_INTENT_KEY);
        switch (editingIntent){
            case CREATE: {
                contact = new Contact(0);
                break;
            }
            case UPDATE: {
                Bundle extras = intent.getExtras();
                contact = (Contact) extras.get(ContactActivity.CONTACT_BUNDLE_KEY);
                break;
            }
        }
    }

    @Override
    public void initialize() {
        getParentView().setContact(contact);
    }

    @Override
    public void acceptButtonClick() {
        IEditView view = getParentView();
        contact = view.extractData(contact.getId());
        if(view.validate()) {
            switch (editingIntent) {
                case UPDATE: {
                    getWriter().updateContact(contact.getId(), contact);
                    Contacts.INSTANCE.setState(ContactDBState.MODIFIED.with(contact.getId()));
                    break;
                }
                case CREATE: {
                    getWriter().addContact(contact);
                    Contacts.INSTANCE.setState(ContactDBState.MODIFIED.with(ContactDBState.NO_ID));
                    break;
                }
            }
            view.goToActivity(null);
        } else {
            view.message(R.string.field_empty_message);
        }
    }

    @Override
    public void onImageReceived() {
        // TODO: 10.08.16 ON IMAGE RECEIVED IMPLEMENTATION
    }
}
