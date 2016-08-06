package com.tudev.firstapp.data.sql;

import java.util.EnumSet;

/**
 * Created by arseniy on 06.08.16.
 */

public interface TableInfoCommand{
    String createTable(TableCreateOptions options);

    String dropTable(TableDropOptions options);

    String alterTable(FieldInfoCommand field);

    String renameTable(String name);

    public static enum TableCreateOptions{
        TEMPORARY,
        IF_NOT_EXISTS,
        WITHOUT_ROWID
    }

    public static enum TableDropOptions{
        IF_EXISTS
    }
}
