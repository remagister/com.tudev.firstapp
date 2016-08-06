package com.tudev.firstapp.data.sql;

/**
 * Created by arseniy on 06.08.16.
 */

public interface IFieldDefinition extends IDatabaseNamedItem {

    public String getDefinition();

    public static  enum FieldType{
        NULL, INTEGER, REAL, TEXT, BLOB
    }

    public static enum FieldModifier{
        NULL, NOT_NULL, PRIMARY_KEY, UNIQUE, AUTOINCREMENT
    }
}
