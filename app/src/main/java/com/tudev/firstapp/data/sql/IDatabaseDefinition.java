package com.tudev.firstapp.data.sql;

import java.util.List;

/**
 * Created by Arseniy on 06.08.16.
 */

public interface IDatabaseDefinition extends IDatabaseNamedItem {

    List<String> initTables();

    List<String> dropTables();

    List<ITableDefinition> getTables();

    void setName(String name);
}
