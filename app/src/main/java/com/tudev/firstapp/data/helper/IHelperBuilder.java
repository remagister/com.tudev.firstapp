package com.tudev.firstapp.data.helper;

import android.database.sqlite.SQLiteOpenHelper;

import com.tudev.firstapp.data.sql.IDatabaseDefinition;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IHelperBuilder {
    SQLiteOpenHelper build(IDatabaseDefinition definition);
}
