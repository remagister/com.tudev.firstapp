package com.tudev.firstapp.data;

import com.tudev.firstapp.data.dao.Contact;
import com.tudev.firstapp.data.dao.ContactDAO;
import com.tudev.firstapp.data.dao.IContactDAO;
import com.tudev.firstapp.data.helper.IHelperBuilder;

import java.util.ArrayList;

/**
 * Created by Саша on 27.07.2016.
 */

public enum Contacts{
    INSTANCE;

    public IContactDAO getContactsDAO(IHelperBuilder builder){
        return new ContactDAO(builder);
    }
}