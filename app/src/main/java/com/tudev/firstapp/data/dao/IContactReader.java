package com.tudev.firstapp.data.dao;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IContactReader {
    List<Contact> getAllContacts();
    List<Contact.ContactSimple> getSimpleContacts();
    Contact getContact(long id);
}
