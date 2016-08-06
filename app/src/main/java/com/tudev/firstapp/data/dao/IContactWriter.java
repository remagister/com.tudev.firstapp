package com.tudev.firstapp.data.dao;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IContactWriter {
    void removeContact(long id);
    void removeContacts(List<Contact.ContactSimple> removal);
    void updateContact(long id, Contact newContact);
    void addContact(Contact contact);
}
