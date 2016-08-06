package com.tudev.firstapp.data.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public class DatabaseDefinition extends DatabaseNamedItem implements IDatabaseDefinition {

    private List<ITableDefinition> tables = new ArrayList<>();

    public DatabaseDefinition(String itemName) {
        super(itemName);
    }

    public DatabaseDefinition table(ITableDefinition table){
        tables.add(table);
        return this;
    }

    @Override
    public List<String> initTables() {
        ArrayList<String> ret = new ArrayList<>();
        for(ITableDefinition table : tables){
            ret.add(table.createTable());
        }
        return ret;
    }

    @Override
    public List<String> dropTables() {
        ArrayList<String> ret = new ArrayList<>();
        for(ITableDefinition table : tables){
            ret.add(table.dropTable());
        }
        return ret;
    }

    @Override
    public List<ITableDefinition> getTables() {
        return Collections.unmodifiableList(tables);
    }

    @Override
    public void setName(String name) {
        super.name = name;
    }
}
