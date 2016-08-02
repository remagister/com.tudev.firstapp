package com.tudev.firstapp.data;

import java.io.Closeable;
import java.util.List;

/**
 * Created by Саша on 02.08.2016.
 */
public interface ContactAccessor extends Closeable {
    public List<Contact> getAllContacts();
    public List<Contact.ContactSimple> getSimpleContacts();
    public Contact getContact(long id);
    public void removeContact(long id);
    public void removeContacts(List<Contact.ContactSimple> removal);
    public void updateContact(long id, Contact newContact);
    public void addContact(Contact contact);

}
