package com.tudev.firstapp.data.sql;

/**
 * Created by arseniy on 06.08.16.
 */

public abstract class DatabaseNamedItem implements IDatabaseNamedItem {

    protected String name = "";
    protected String schema = null;

    public DatabaseNamedItem(String itemName){
        name = itemName;
    }

    public DatabaseNamedItem(String name, String schema){
        this.name = name;
        this.schema = schema;
    }

    public void setSchema(String schema){
        this.schema = schema;
    }

    @Override
    public String getItemName() {
        return name;
    }

}
