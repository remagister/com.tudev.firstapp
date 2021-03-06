package com.tudev.firstapp.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tudev.firstapp.data.Contacts;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.dao.IContactReader;
import com.tudev.firstapp.data.dao.IContactWriter;
import com.tudev.firstapp.data.helper.SQLiteHelperBuilder;
import com.tudev.firstapp.view.IView;

import java.io.IOException;

/**
 * Created by arseniy on 10.08.16.
 */

public abstract class ContactPresenterBase<V extends IView> implements IPresenter {

    private IContactDAO contacts;
    private IView parentView;
    private boolean disposal;

    public ContactPresenterBase(V view) {
        this(view, false);
    }

    public ContactPresenterBase(V view, boolean onDisposalNeeded){
        parentView = view;
        disposal = onDisposalNeeded;
    }

    public V getParentView() {
        return (V) parentView;
    }

    protected IContactReader getReader(){
        return contacts.getReader();
    }

    protected IContactWriter getWriter(){
        return contacts.getWriter();
    }

    protected IContactDAO getDAO(){
        return contacts;
    }

    @Override
    public void onCreate(Context context) {
        contacts = Contacts.INSTANCE.getDao();
    }

    @Override
    public void onLoadState(Bundle bundle) {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSaveState(Bundle bundle) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void onDestroy() {
        if(disposal) {
            Contacts.INSTANCE.tryRelease();
            Log.d("DESTROY", "trying to destroy a disposable activity");
        }
    }
}
