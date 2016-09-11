package com.tudev.firstapp.data;

import android.app.Application;

import com.tudev.firstapp.ContactDBState;
import com.tudev.firstapp.app.ContactsApplication;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.IHelperBuilder;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Саша on 27.07.2016.
 */

public enum Contacts {
    INSTANCE;

    private static final int TIMEOUT_MILLIS = 200;
    private ReferenceQueue<IContactDAO> referenceQueue = new ReferenceQueue<>();
    private WeakReference<IContactDAO> daoWeakReference;
    private PhantomReference<IContactDAO> daoPhantomReference;
    private int queueSize = 0;
    private IHelperBuilder helperBuilder;

    public IContactDAO getDao() {
        IContactDAO strongReference = daoWeakReference == null ? null : daoPhantomReference.get();
        if (strongReference == null) {
            strongReference = ContactsApplication.getApplicationInstance().openContactDao();
            daoWeakReference = new WeakReference<>(strongReference);
            daoPhantomReference = new PhantomReference<>(strongReference, referenceQueue);

            try {
                Reference<? extends IContactDAO> phantom = referenceQueue.poll();
                if (phantom != null) {
                    phantom.get().close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return strongReference;
    }

    public void tryRelease() {
        try {
            Reference<? extends IContactDAO> phantom = referenceQueue.remove(TIMEOUT_MILLIS);
            if (phantom != null) {
                phantom.get().close();
                phantom.clear();
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private ContactDBState state = ContactDBState.INTACT;

    public void setState(ContactDBState state) {
        this.state = state;
    }

    public ContactDBState getState() {
        return state;
    }

    public void reset() {
        state = ContactDBState.INTACT.with(ContactDBState.NO_ID);
    }
}