package com.tudev.firstapp.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tudev.firstapp.data.helper.IHelperBuilder;
import com.tudev.firstapp.data.sql.DatabaseDefinition;
import com.tudev.firstapp.data.sql.FieldDefinition;
import com.tudev.firstapp.data.sql.IDatabaseDefinition;
import com.tudev.firstapp.data.sql.IFieldDefinition;
import com.tudev.firstapp.data.sql.TableDefinition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import static com.tudev.firstapp.data.dao.ContactDataContract.ContactEntry;

/**
 * Created by arseniy on 06.08.16.
 */

public class ContactDAO implements IContactDAO {

    private static final String DB_NAME = "contacts.db";

    private IDatabaseDefinition databaseDefinition;

    private SQLiteOpenHelper helper;
    private SQLiteDatabase readable;
    private SQLiteDatabase writable;

    private List<Contact.ContactSimple> contactList = new ArrayList<>();

    public ContactDAO(IHelperBuilder builder) {
        databaseDefinition = new DatabaseDefinition(DB_NAME)
            .table(new TableDefinition(ContactEntry.TABLE_CONTACTS)
                    .column(new FieldDefinition(ContactEntry._ID,
                            IFieldDefinition.FieldType.INTEGER,
                            EnumSet.of(IFieldDefinition.FieldModifier.PRIMARY_KEY,
                                    IFieldDefinition.FieldModifier.AUTOINCREMENT)))
                .column(new FieldDefinition(ContactEntry.CONTACTS_FIELD_NAME,
                        IFieldDefinition.FieldType.TEXT))
                .column(new FieldDefinition(ContactEntry.CONTACTS_FIELD_EMAIL,
                        IFieldDefinition.FieldType.TEXT))
                .column(new FieldDefinition(ContactEntry.CONTACTS_FIELD_PHONE,
                        IFieldDefinition.FieldType.TEXT))
                .column(new FieldDefinition(ContactEntry.CONTACTS_FIELD_IMAGE,
                        IFieldDefinition.FieldType.TEXT)));
        helper = builder.build(databaseDefinition);
    }

    @Override
    public IContactReader getReader() {
        if(readable == null) {
            if(writable != null){
                writable.close();
                writable = null;
            }
            readable = helper.getReadableDatabase();
        }
        return new InternalReader(readable);
    }

    @Override
    public IContactWriter getWriter() {
        if(writable == null) {
            if(readable != null){
                readable.close();
                readable = null;
            }
            writable = helper.getWritableDatabase();
        }
        return new InternalWriter(writable);
    }

    @Override
    public void invalidate() {
        contactList.clear();
        getReader().getSimpleContacts();
    }

    @Override
    public void close() throws IOException {
        if(readable != null){
            readable.close();
        }
        if(writable != null){
            writable.close();
        }
        helper.close();
    }

    private static long getId(Cursor cursor){
        return cursor.getLong(cursor.getColumnIndex(ContactEntry._ID));
    }
    private static String getString(Cursor cursor, String field){
        return cursor.getString(cursor.getColumnIndex(field));
    }

    private void fillListSimple(List<Contact.ContactSimple> list, Cursor cursor){
        while (cursor.moveToNext()) {
            Contact.ContactSimple contact = new Contact.ContactSimple(getId(cursor));
            contact.setName(getString(cursor, ContactEntry.CONTACTS_FIELD_NAME));
            contact.setPhone(getString(cursor, ContactEntry.CONTACTS_FIELD_PHONE));
            contact.setImageThumb(getString(cursor, ContactEntry.CONTACTS_FIELD_IMAGE));
            list.add(contact);
        }
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
    private void cacheUpdate(Contact.ContactSimple newContact){
        if (contactList != null) {
            Log.d(getClass().getName(), "cache update: contact id " + newContact.getId());
            Log.d(getClass().getName(), "cache update: list " + contactList.size());
            int index = contactList.indexOf(newContact);
            Log.d(getClass().getName(), "cache update: index " + index);
            contactList.set(index, newContact);
        }
    }
    private void cacheAdd(Contact.ContactSimple contact){
        if (contactList != null) {
            contactList.add(contact);
        }
    }

    private class InternalWriter implements IContactWriter{

        private SQLiteDatabase externalDb;

        public InternalWriter(SQLiteDatabase db) {
            externalDb = db;
        }

        @Override
        public void removeContact(long id) {
            externalDb.delete(ContactEntry.TABLE_CONTACTS, ContactEntry._ID + " = ?",
                    new String[]{String.valueOf(id)});
            cacheRemove(id);
        }

        @Override
        public void removeContacts(List<Contact.ContactSimple> removal) {
            if(!removal.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                builder.append("DELETE FROM " + ContactEntry.TABLE_CONTACTS + ContactEntry._ID
                        + " IN (");
                for (Contact.ContactSimple contact : removal) {
                    builder.append(String.valueOf(contact.getId())).append(",");
                }
                builder.deleteCharAt(builder.length() - 1);   // remove last ','
                builder.append(')');
                externalDb.execSQL(builder.toString());
                cacheRemove(removal);
            }
        }

        private ContentValues getValues(Contact contact){
            ContentValues val = new ContentValues();
            val.put(ContactEntry.CONTACTS_FIELD_NAME, contact.getName());
            val.put(ContactEntry.CONTACTS_FIELD_EMAIL, contact.getEmail());
            val.put(ContactEntry.CONTACTS_FIELD_PHONE, contact.getPhone());
            val.put(ContactEntry.CONTACTS_FIELD_IMAGE, contact.getImage());
            return val;
        }

        @Override
        public void updateContact(long id, Contact newContact) {
            externalDb.update(ContactEntry.TABLE_CONTACTS, getValues(newContact),
                    ContactEntry._ID + " = ?",
                    new String[] {String.valueOf(id)});
            Log.d(getClass().getName(), "Contact updated: " + newContact.toString());
            cacheUpdate(new Contact.ContactSimple(id, newContact));
        }

        @Override
        public void addContact(Contact contact) {
            long id = externalDb.insert(ContactEntry.TABLE_CONTACTS, null, getValues(contact));
            cacheAdd(new Contact.ContactSimple(id, contact));
        }
    }

    private class InternalReader implements IContactReader {

        private SQLiteDatabase externalDb;

        public InternalReader(SQLiteDatabase db) {
            externalDb = db;
        }

        private Contact getContact(Cursor cursor){
            Contact contact = new Contact(getId(cursor));
            contact.setPhone(getString(cursor, ContactEntry.CONTACTS_FIELD_PHONE));
            contact.setName(getString(cursor, ContactEntry.CONTACTS_FIELD_NAME));
            contact.setEmail(getString(cursor, ContactEntry.CONTACTS_FIELD_EMAIL));
            contact.setImage(getString(cursor, ContactEntry.CONTACTS_FIELD_IMAGE));
            return contact;
        }

        @Override
        public List<Contact> getAllContacts() {
            Cursor cursor = externalDb.query(ContactEntry.TABLE_CONTACTS,
                    null, null, null, null, null, null);
            List<Contact> ret = new ArrayList<>();
            while (cursor.moveToNext()){
                ret.add(getContact(cursor));
            }
            cursor.close();
            return ret;
        }

        @Override
        public List<Contact.ContactSimple> getSimpleContacts() {
            if(contactList.isEmpty()) {
                String fields[] = new String[]{ContactEntry._ID,
                        ContactEntry.CONTACTS_FIELD_NAME,
                        ContactEntry.CONTACTS_FIELD_PHONE,
                        ContactEntry.CONTACTS_FIELD_IMAGE};
                Cursor cursor = externalDb.query(ContactEntry.TABLE_CONTACTS,
                        fields, null, null, null, null, null);
                fillListSimple(contactList, cursor);
                cursor.close();
            }
            return contactList;
        }

        @Override
        public Contact getContact(long id) {
            Cursor cursor = externalDb.query(ContactEntry.TABLE_CONTACTS, null, ContactEntry._ID + " = ?",
                    new String[]{String.valueOf(id)}, null, null, null);
            cursor.moveToFirst();
            Contact ret = getContact(cursor);
            cursor.close();
            return ret;
        }
    }
}
