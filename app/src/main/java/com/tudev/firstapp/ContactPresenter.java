package com.tudev.firstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.SQLiteHelperBuilder;

/**
 * Created by arseniy on 07.08.16.
 */

public class ContactPresenter implements IContactPresenter {

    private IContactView parentView;
    private Contact contact;
    private IContactDAO contacts;

    public ContactPresenter(IContactView view) {
        parentView = view;
    }

    @Override
    public void onCreate(Context context) {
        Bundle extras = ((Activity) context).getIntent().getExtras();
        if (extras != null) {
            contact = (Contact) extras.get(ContactActivity.CONTACT_BUNDLE_KEY);
        }
        else{
            // something went wrong
            String undefined = context.getString(R.string.undefined_contact);
            contact = new Contact(undefined, undefined);
        }
        parentView.setContact(contact);
        contacts = Contacts.INSTANCE.getDao();
        if(contacts == null){
            contacts = new ContactDAO(new SQLiteHelperBuilder(context));
            Contacts.INSTANCE.setDao(contacts);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        if(Contacts.INSTANCE.getState().hasId()) {
            contact = contacts.getReader().getContact(Contacts.INSTANCE.getState().getId());
            parentView.setContact(contact);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onEditButtonClick() {
        Intent intent = parentView.createIntent(EditContactActivity.class);
        intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, contact);
        intent.putExtra(MainPresenter.CONTACT_EDIT_INTENT_KEY, ContactActionIntent.UPDATE);
        parentView.goToActivity(intent);
    }
}
