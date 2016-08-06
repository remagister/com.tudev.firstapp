package com.tudev.firstapp.data;

import android.database.sqlite.SQLiteDatabase;

import com.tudev.firstapp.data.helper.IHelperBuilder;
import com.tudev.firstapp.data.sql.IDatabaseDefinition;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by arseniy on 06.08.16.
 */

public abstract class DAOBase implements Closeable {

    private IHelperBuilder internalBuilder;

    public DAOBase(IHelperBuilder builder) {
        internalBuilder = builder;
    }

    public abstract IDatabaseDefinition getDefinition();

    protected SQLiteDatabase getReader() {
        return internalBuilder.build(getDefinition()).getReadableDatabase();
    }
    protected SQLiteDatabase getWriter(){
        return internalBuilder.build(getDefinition()).getWritableDatabase();
    }


    @Override
    public void close() throws IOException {

    }
}
