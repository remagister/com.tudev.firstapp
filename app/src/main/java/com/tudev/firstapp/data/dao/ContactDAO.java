package com.tudev.firstapp.data.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tudev.firstapp.data.helper.IHelperBuilder;
import com.tudev.firstapp.data.sql.DatabaseDefinition;
import com.tudev.firstapp.data.sql.FieldDefinition;
import com.tudev.firstapp.data.sql.IDatabaseDefinition;
import com.tudev.firstapp.data.sql.IFieldDefinition;
import com.tudev.firstapp.data.sql.TableDefinition;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static com.tudev.firstapp.data.dao.ContactDataContract.ContactEntry;

/**
 * Created by arseniy on 06.08.16.
 */

public class ContactDAO implements IContactDAO {

    public static final String DB_NAME = "contacts.db";

    private IHelperBuilder builder;
    private IDatabaseDefinition databaseDefinition;

    private SQLiteDatabase readable;
    private SQLiteDatabase writable;

    public ContactDAO(IHelperBuilder builder) {
        this.builder = builder;
        databaseDefinition = new DatabaseDefinition(DB_NAME)
            .table(new TableDefinition(ContactEntry.TABLE_CONTACTS)
                    .constraint(new FieldDefinition(ContactEntry._ID,
                            IFieldDefinition.FieldType.INTEGER,
                            EnumSet.of(IFieldDefinition.FieldModifier.PRIMARY_KEY,
                                    IFieldDefinition.FieldModifier.AUTOINCREMENT)))
                .constraint(new FieldDefinition(ContactEntry.CONTACTS_FIELD_NAME,
                        IFieldDefinition.FieldType.TEXT))
                .constraint(new FieldDefinition(ContactEntry.CONTACTS_FIELD_EMAIL,
                        IFieldDefinition.FieldType.TEXT))
                .constraint(new FieldDefinition(ContactEntry.CONTACTS_FIELD_PHONE,
                        IFieldDefinition.FieldType.TEXT))
                .constraint(new FieldDefinition(ContactEntry.CONTACTS_FIELD_IMAGE,
                        IFieldDefinition.FieldType.BLOB)));
    }

    @Override
    public IContactReader openReader() {
        if(readable == null) {
            readable = builder.build(databaseDefinition).getReadableDatabase();
        }
        // TODO: 06.08.16  Make contact reader implementation
        return null;
    }

    @Override
    public IContactWriter openWriter() {
        if(writable == null) {
            writable = builder.build(databaseDefinition).getWritableDatabase();
        }
        // TODO: 06.08.16  Make contact writer implementation
        return null;
    }

    @Override
    public void invalidate() {

    }

    @Override
    public void close() throws IOException {
        if(readable != null){
            readable.close();
        }
        if(writable != null){
            writable.close();
        }
    }

    private class InternalWriter implements IContactWriter{

        @Override
        public void removeContact(long id) {

        }

        @Override
        public void removeContacts(List<Contact.ContactSimple> removal) {

        }

        @Override
        public void updateContact(long id, Contact newContact) {

        }

        @Override
        public void addContact(Contact contact) {

        }
    }

    private class InternalReader implements IContactReader {

        @Override
        public List<Contact> getAllContacts() {
            return null;
        }

        @Override
        public List<Contact.ContactSimple> getSimpleContacts() {
            return null;
        }

        @Override
        public Contact getContact(long id) {
            return null;
        }
    }
}
