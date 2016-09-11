package com.tudev.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.presenter.ContactPresenterBase;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public class MainPresenter extends ContactPresenterBase<IMainView> implements IMainPresenter {

    static final String CONTACT_EDIT_INTENT_KEY = "CONTACT_EDIT_INTENT_KEY";

    private ContactAdapterState state = ContactAdapterState.NORMAL;

    MainPresenter(IMainView parentView) {
        super(parentView, true);
    }

    @Override
    public void onLoadState(Bundle bundle) {
        state = (ContactAdapterState) bundle.getSerializable(ContactAdapterState.STATE_KEY);
        super.onLoadState(bundle);
    }

    @Override
    public void initialize() {
        IMainView view = getParentView();
        view.setContactsList(getReader().getSimpleContacts());
        view.initState(state);
    }

    private void resetData(){
        if(Contacts.INSTANCE.getState() == ContactDBState.MODIFIED) {
            getParentView().notifyDataChanged();
            Contacts.INSTANCE.reset();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        resetData();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetData();
    }

    @Override
    public void onSaveState(Bundle bundle) {
        bundle.putSerializable(ContactAdapterState.STATE_KEY, state);
        super.onSaveState(bundle);
    }


    @Override
    public void buttonClicked(AddButtonIntent state) {
        IMainView view = getParentView();
        switch (state){
            case ADD:{
                Intent createIntent = view.createIntent(EditContactActivity.class);
                createIntent.putExtra(CONTACT_EDIT_INTENT_KEY, ContactActionIntent.CREATE);
                view.goToActivity(createIntent);
                break;
            }
            case REMOVE:{
                List<Contact.ContactSimple> removeList = view.getRemoveList();
                getWriter().removeContacts(removeList);
                view.notifyDataChanged();
                view.initState(this.state = ContactAdapterState.NORMAL);
                break;
            }
        }
    }

    @Override
    public void itemClicked(Contact.ContactSimple contactSimple) {
        IMainView view = getParentView();
        Intent intent = view.createIntent(ContactActivity.class);
        Contact toSend = getReader().getContact(contactSimple.getId());
        intent.putExtra(ContactActivity.CONTACT_BUNDLE_KEY, toSend);
        view.goToActivity(intent);
    }

    @Override
    public void onItemLongClick() {
        state = ContactAdapterState.SELECTION;
        getParentView().initState(state);
    }

    @Override
    public void onBackPressed() {
        if(state == ContactAdapterState.SELECTION) {
            state = ContactAdapterState.NORMAL;
            getParentView().initState(state);
        }
    }
}
