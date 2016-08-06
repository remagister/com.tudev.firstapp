package com.tudev.firstapp;

import android.content.Context;
import android.content.Intent;

import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.SQLiteHelperBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public class MainPresenter implements IMainPresenter {

    public static final String CONTACT_EDIT_INTENT_KEY = "CONTACT_EDIT_INTENT_KEY";

    private IContactDAO contacts;
    private IMainView parentView;

    public MainPresenter(IMainView parentView) {
        this.parentView = parentView;
    }


    @Override
    public void onCreate(Context context) {
        contacts = Contacts.INSTANCE.getDao();
        if(contacts == null){
            contacts = new ContactDAO(new SQLiteHelperBuilder(context));
            Contacts.INSTANCE.setDao(contacts);
        }

        parentView.setContactsList(contacts.getReader().getSimpleContacts());
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        if(Contacts.INSTANCE.getState() == ContactDBState.MODIFIED) {
            contacts.invalidate();
            parentView.notifyDataChanged();
            Contacts.INSTANCE.reset();
        }
    }

    @Override
    public void onDestroy() {
        try {
            contacts.close();
        } catch (IOException e) {
            parentView.message(e.getMessage());
        }
    }

    @Override
    public void buttonClicked(AddButtonIntent state) {
        switch (state){
            case ADD:{
                Intent createIntent = parentView.createIntent(EditContactActivity.class);
                createIntent.putExtra(CONTACT_EDIT_INTENT_KEY, ContactActionIntent.CREATE);
                parentView.goToActivity(createIntent);
                break;
            }
            case REMOVE:{
                List<Contact.ContactSimple> removeList = parentView.getRemoveList();
                contacts.getWriter().removeContacts(removeList);
                parentView.notifyDataChanged();
                break;
            }
        }
    }

    @Override
    public void itemClicked(Contact.ContactSimple contactSimple) {
        Intent intent = parentView.createIntent(ContactActivity.class);
        Contact toSend = contacts.getReader().getContact(contactSimple.getId());
        intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, toSend);
        parentView.goToActivity(intent);
    }
}
