package com.tudev.firstapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.tudev.firstapp.data.dao.ContactDataContract.ContactEntry;
/**
 * Created by Саша on 02.08.2016.
 */
public class SQLContactHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "contacts.db";
    public static final String SEP = ",";
    public static final int DB_VER = 1;
    public static final String CONTACTS_FIELD_TEXT_TYPE = " TEXT";  // whitespace 4 syntax
    public static final String CONTACTS_FIELD_ASSET_TYPE = " BLOB";

    public static final String CREATE_TABLE = "CREATE TABLE " + ContactEntry.TABLE_CONTACTS + "("
            + ContactEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + SEP
            + ContactEntry.CONTACTS_FIELD_NAME + CONTACTS_FIELD_TEXT_TYPE + SEP
            + ContactEntry.CONTACTS_FIELD_EMAIL + CONTACTS_FIELD_TEXT_TYPE + SEP
            + ContactEntry.CONTACTS_FIELD_PHONE + CONTACTS_FIELD_TEXT_TYPE + SEP
            + ContactEntry.CONTACTS_FIELD_IMAGE + CONTACTS_FIELD_ASSET_TYPE + ")";

    public SQLContactHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public SQLContactHelper(Context context, String name){
        super(context, name, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // if not exists
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContactEntry.TABLE_CONTACTS);
        onCreate(sqLiteDatabase);
    }
}
