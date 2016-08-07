package com.tudev.firstapp.data;

import com.tudev.firstapp.ContactDBState;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.IHelperBuilder;

import java.util.ArrayList;

/**
 * Created by Саша on 27.07.2016.
 */

public enum Contacts{
    INSTANCE;

    private IContactDAO dao;

    public void setDao(IContactDAO dao){
        this.dao = dao;
    }

    public IContactDAO getDao(IHelperBuilder builder){
        if (dao == null) {
            dao = new ContactDAO(builder);
        }
        return dao;
    }

    private ContactDBState state = ContactDBState.INTACT;

    public void setState(ContactDBState state) {
        this.state = state;
    }

    public ContactDBState getState() { return state; }

    public void reset(){
        state = ContactDBState.INTACT.with(ContactDBState.NO_ID);
    }
}