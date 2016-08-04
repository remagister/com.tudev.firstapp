package com.tudev.firstapp.data;

import static com.tudev.firstapp.data.ContactDataContract.ContactEntry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.BaseAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Саша on 02.08.2016.
 */

public class SimpleContactDatabase implements ContactAccessor{

    private SQLiteOpenHelper internalHelper;
    private List<Contact.ContactSimple> contactList;


    public SimpleContactDatabase(SQLiteOpenHelper helper){
        internalHelper = helper;
    }

    private long getId(Cursor cursor){
        return cursor.getLong(cursor.getColumnIndex(ContactEntry._ID));
    }
    private String getString(Cursor cursor, String field){
        return cursor.getString(cursor.getColumnIndex(field));
    }

    private byte[] getBytes(Cursor cursor, String field){
        return cursor.getBlob(cursor.getColumnIndex(field));
    }
    
    @Override
    public void close() throws IOException {
        internalHelper.close();
    }

    @Override
    public List<Contact> getAllContacts() {
        SQLiteDatabase db = internalHelper.getReadableDatabase();
        Cursor cursor = db.query(ContactEntry.TABLE_CONTACTS, null, null, null, null, null, null);
        List<Contact> ret = new ArrayList<>();
        while (cursor.moveToNext()){
            Contact contact = new Contact(getId(cursor));
            contact.setPhone(getString(cursor, ContactEntry.CONTACTS_FIELD_PHONE));
            contact.setName(getString(cursor, ContactEntry.CONTACTS_FIELD_NAME));
            contact.setEmail(getString(cursor, ContactEntry.CONTACTS_FIELD_EMAIL));
            ret.add(contact);
        }
        cursor.close();
        db.close();
        return ret;
    }

    private void fillListSimple(List<Contact.ContactSimple> list, Cursor cursor){
        while (cursor.moveToNext()) {
            Contact.ContactSimple contact = new Contact.ContactSimple(getId(cursor));
            contact.setName(getString(cursor, ContactEntry.CONTACTS_FIELD_NAME));
            contact.setPhone(getString(cursor, ContactEntry.CONTACTS_FIELD_PHONE));
            list.add(contact);
        }
    }

    @Override
    public List<Contact.ContactSimple> getSimpleContacts() {
        if(contactList == null) {
            SQLiteDatabase db = internalHelper.getReadableDatabase();
            String fields[] = new String[]{ContactEntry._ID,
                    ContactEntry.CONTACTS_FIELD_NAME,
                    ContactEntry.CONTACTS_FIELD_PHONE};
            Cursor cursor = db.query(ContactEntry.TABLE_CONTACTS, fields, null, null, null, null, null);
            contactList = new ArrayList<>();
            fillListSimple(contactList, cursor);
            cursor.close();
            db.close();
        }
        return Collections.unmodifiableList(contactList);
    }

    @Override
    public Contact getContact(long id) {
        SQLiteDatabase db = internalHelper.getReadableDatabase();
        Cursor cursor = db.query(ContactEntry.TABLE_CONTACTS, null, ContactEntry._ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        Contact ret = new Contact(getId(cursor));
        ret.setPhone(getString(cursor, ContactEntry.CONTACTS_FIELD_PHONE));
        ret.setName(getString(cursor, ContactEntry.CONTACTS_FIELD_NAME));
        ret.setEmail(getString(cursor, ContactEntry.CONTACTS_FIELD_EMAIL));
        cursor.close();
        db.close();
        return ret;
    }

    private void cacheRemove(long id){
        if(contactList != null){
            contactList.remove(new Contact.ContactSimple(id));
        }
    }

    private void cacheRemove(List<Contact.ContactSimple> removal){
        if(contactList != null){
            contactList.removeAll(removal);
        }
    }

    @Override
    public void removeContact(long id) {
        SQLiteDatabase db = internalHelper.getReadableDatabase();
        db.delete(ContactEntry.TABLE_CONTACTS, ContactEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        cacheRemove(id);
    }

    @Override
    public void removeContacts(List<Contact.ContactSimple> removal) {
        if(!removal.isEmpty()) {
            SQLiteDatabase db = internalHelper.getReadableDatabase();
            StringBuilder builder = new StringBuilder();
            builder.append("DELETE FROM " + ContactEntry.TABLE_CONTACTS + ContactEntry._ID
                    + " IN (");
            for (Contact.ContactSimple contact : removal) {
                builder.append(String.valueOf(contact.getId())).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);   // remove last ','
            builder.append(')');
            db.execSQL(builder.toString());
            db.close();
            cacheRemove(removal);
        }
    }

    private void cacheUpdate(Contact.ContactSimple newContact){
        if (contactList != null) {
            int index = contactList.indexOf(newContact);
            contactList.set(index, newContact);
        }
    }

    @Override
    public void updateContact(long id, Contact newContact) {
        SQLiteDatabase db = internalHelper.getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put(ContactEntry.CONTACTS_FIELD_NAME, newContact.getName());
        val.put(ContactEntry.CONTACTS_FIELD_EMAIL, newContact.getEmail());
        val.put(ContactEntry.CONTACTS_FIELD_PHONE, newContact.getPhone());
        val.put(ContactEntry.CONTACTS_FIELD_IMAGE, newContact.getImage());

        db.update(ContactEntry.TABLE_CONTACTS, val, ContactEntry._ID + " = ?",
                new String[] {String.valueOf(id)});
        db.close();
        cacheUpdate(new Contact.ContactSimple(id, newContact));
    }

    private void cacheAdd(Contact.ContactSimple contact){
        if (contactList != null) {
            contactList.add(contact);
        }
    }

    @Override
    public void addContact(Contact contact) {
        SQLiteDatabase db = internalHelper.getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put(ContactEntry.CONTACTS_FIELD_NAME, contact.getName());
        val.put(ContactEntry.CONTACTS_FIELD_EMAIL, contact.getEmail());
        val.put(ContactEntry.CONTACTS_FIELD_PHONE, contact.getPhone());
        val.put(ContactEntry.CONTACTS_FIELD_IMAGE, contact.getImage());
        long id = db.insert(ContactEntry.TABLE_CONTACTS, null, val);
        db.close();

        cacheAdd(new Contact.ContactSimple(id, contact));
    }

    @Override
    public void invalidate() {
        // list = null, soo is not acceptable here because it is not an actual updating
        SQLiteDatabase db = internalHelper.getReadableDatabase();
        String fields[] = new String[]{ContactEntry._ID,
                ContactEntry.CONTACTS_FIELD_NAME,
                ContactEntry.CONTACTS_FIELD_PHONE};
        Cursor cursor = db.query(ContactEntry.TABLE_CONTACTS, fields, null, null, null, null, null);
        contactList.clear();
        fillListSimple(contactList, cursor);
        cursor.close();
        db.close();
    }
}
