package com.tudev.firstapp.data.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arseniy on 11.08.16.
 */

public class BlankDefinition extends DatabaseNamedItem implements IDatabaseDefinition {

    public BlankDefinition(String itemName) {
        super(itemName);
    }

    @Override
    public List<String> initTables() {
        return new ArrayList<>();
    }

    @Override
    public List<String> dropTables() {
        return new ArrayList<>();
    }

    @Override
    public List<ITableDefinition> getTables() {
        return new ArrayList<>();
    }

    @Override
    public void setName(String name) {
        super.name = name;
    }
}
