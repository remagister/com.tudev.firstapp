package com.tudev.firstapp.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tudev.firstapp.data.sql.IDatabaseDefinition;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public class SQLiteHelperBuilder implements IHelperBuilder {

    private Context context;
    private static final int DB_VERSION = 1;

    public SQLiteHelperBuilder(Context context){
        this.context = context;
    }

    @Override
    public SQLiteOpenHelper build(IDatabaseDefinition definition) {
        return new SQLiteHelper(definition);
    }

    public class SQLiteHelper extends SQLiteOpenHelper {

        private IDatabaseDefinition definition;

        protected SQLiteHelper(IDatabaseDefinition definition){
            super(context, definition.getItemName(), null, DB_VERSION);
            this.definition = definition;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            List<String> statements = definition.initTables();
            for(String createTable : statements){
                sqLiteDatabase.execSQL(createTable);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            List<String> statements = definition.dropTables();
            for(String dropTable : statements){
                sqLiteDatabase.execSQL(dropTable);
            }
            onCreate(sqLiteDatabase);
        }
    }
}
