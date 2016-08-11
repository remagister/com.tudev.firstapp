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
import com.tudev.firstapp.presenter.ContactPresenterBase;

/**
 * Created by arseniy on 07.08.16.
 */

public class ContactPresenter extends ContactPresenterBase<IContactView> implements IContactPresenter {

    private Contact contact;

    public ContactPresenter(IContactView view) {
        super(view, true);
    }

    @Override
    public void onCreate(Context context) {
        super.onCreate(context);
        Bundle extras = ((Activity) context).getIntent().getExtras();
        if (extras != null) {
            contact = (Contact) extras.get(ContactActivity.CONTACT_BUNDLE_KEY);
        }
        else{
            // something went wrong
            String undefined = context.getString(R.string.undefined_contact);
            contact = new Contact(undefined, undefined);
        }
    }

    @Override
    public void initialize() {
        getParentView().setContact(contact);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Contacts.INSTANCE.getState().hasId()) {
            contact = getReader().getContact(Contacts.INSTANCE.getState().getId());
            getParentView().setContact(contact);
        }
    }

    @Override
    public void onEditButtonClick() {
        Intent intent = getParentView().createIntent(EditContactActivity.class);
        intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, contact);
        intent.putExtra(MainPresenter.CONTACT_EDIT_INTENT_KEY, ContactActionIntent.UPDATE);
        getParentView().goToActivity(intent);
    }
}
