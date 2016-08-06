package com.tudev.firstapp.data.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public class TableDefinition extends DatabaseNamedItem implements ITableDefinition {

    private List<IFieldDefinition> fields = new ArrayList<>();
    private EnumSet<TableDropOptions> dropOptions = EnumSet.noneOf(TableDropOptions.class);
    private EnumSet<TableCreateOptions> createOptions = EnumSet.noneOf(TableCreateOptions.class);

    public TableDefinition(String itemName) {
        super(itemName);
    }

    public TableDefinition constraint(IFieldDefinition field){
        fields.add(field);
        return this;
    }

    public TableDefinition with(EnumSet<TableCreateOptions> options){
        createOptions = options;
        return this;
    }

    public TableDefinition whenDrop(EnumSet<TableDropOptions> options){
        dropOptions = options;
        return this;
    }

    public void remove(IFieldDefinition field){
        for (int i = 0; i < fields.size(); ++i) {
            if(field.equals(fields.get(i))){
                fields.remove(i);
                break;
            }
        }
    }

    @Override
    public String createTable() {
        StringBuilder ret = new StringBuilder();
        ret.append("CREATE TABLE ");
        if(super.schema != null) {
            ret.append(super.schema).append('.');
        }
        // TODO: 06.08.16 CREATION OPTIONS INSERT
        ret.append(super.name).append(" (");
        for(IFieldDefinition f : fields){
            ret.append(f.getDefinition()).append(" , ");
        }
        ret.deleteCharAt(ret.length()-1).append(')');
        return ret.toString();
    }

    @Override
    public String dropTable() {
        StringBuilder ret = new StringBuilder();
        ret.append("DROP TABLE ");
        if(dropOptions.contains(TableDropOptions.IF_EXISTS)){
            ret.append("IF EXISTS ");
        }
        if(super.schema != null) {
            ret.append(super.schema).append('.');
        }
        ret.append(super.name);
        return ret.toString();
    }

    @Override
    public String alterTable(IFieldDefinition field) {
        StringBuilder ret = new StringBuilder();
        ret.append("ALTER TABLE ");
        if(super.schema != null) {
            ret.append(super.schema).append('.');
        }
        ret.append(super.name).append(" ADD COLUMN ").append(
                field.getDefinition()
        );
        fields.add(field);
        return ret.toString();
    }

    @Override
    public String renameTable(String name) {
        StringBuilder ret = new StringBuilder();
        ret.append("ALTER TABLE ");
        if(super.schema != null) {
            ret.append(super.schema).append('.');
        }
        ret.append(super.name).append(" RENAME TO ").append(name);
        return ret.toString();
    }

    @Override
    public List<IFieldDefinition> getFields() {
        return Collections.unmodifiableList(fields);
    }

}
