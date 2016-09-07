package com.tudev.firstapp.data.dao;

import android.provider.BaseColumns;

/**
 * Created by Саша on 02.08.2016.
 */
public class ContactDataContract {

    public ContactDataContract() {}

    // for _id & _count fields in-box
    public static final class ContactEntry implements BaseColumns{
        public static final String TABLE_CONTACTS = "contacts";
        public static final String CONTACTS_FIELD_NAME = "contact_name";
        public static final String CONTACTS_FIELD_EMAIL = "contact_email";
        public static final String CONTACTS_FIELD_PHONE = "contact_phone";
        public static final String CONTACTS_FIELD_IMAGE = "contact_image";
        public static final String CONTACTS_FIELD_BDAY = "contact_birth";

    }
}
