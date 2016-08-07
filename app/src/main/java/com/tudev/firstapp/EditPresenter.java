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

/**
 * Created by arseniy on 07.08.16.
 */

public class EditPresenter implements IEditPresenter {

    private IEditView parentView;
    private IContactDAO contacts;
    private ContactActionIntent editingIntent;
    private Context context;
    private Contact contact;

    public EditPresenter(IEditView parentView) {
        this.parentView = parentView;
    }

    @Override
    public void onCreate(Context context) {
        this.context = context;
        contacts = Contacts.INSTANCE.getDao(new SQLiteHelperBuilder(context.getApplicationContext()));
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
        parentView.setContact(contact);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void acceptButtonClick() {
        Log.d(getClass().getName(), contact.toString());
        contact = parentView.extractData(contact.getId());
        if(parentView.validate()) {
            switch (editingIntent) {
                case UPDATE: {
                    contacts.getWriter().updateContact(contact.getId(), contact);
                    Contacts.INSTANCE.setState(ContactDBState.MODIFIED.with(contact.getId()));
                    break;
                }
                case CREATE: {
                    contacts.getWriter().addContact(contact);
                    Contacts.INSTANCE.setState(ContactDBState.MODIFIED.with(ContactDBState.NO_ID));
                    break;
                }
            }
            parentView.goToActivity(null);
        } else {
            parentView.message(context.getString(R.string.field_empty_message));
        }
    }
}
