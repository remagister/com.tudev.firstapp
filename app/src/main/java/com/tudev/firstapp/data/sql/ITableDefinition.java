package com.tudev.firstapp.data.sql;

import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public interface ITableDefinition extends IDatabaseNamedItem {
    String createTable();

    String dropTable();

    String alterTable(IFieldDefinition field);

    String renameTable(String name);

    List<IFieldDefinition> getFields();

    public static enum TableCreateOptions{
        TEMPORARY,
        IF_NOT_EXISTS,
        WITHOUT_ROWID
    }

    public static enum TableDropOptions{
        IF_EXISTS
    }
}
