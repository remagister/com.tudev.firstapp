package com.tudev.firstapp.data.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arseniy on 06.08.16.
 */

public class TableDefinition extends DatabaseItem implements TableInfoCommand {
    private List<FieldInfoCommand> fields = new ArrayList<>();

    public TableDefinition(String itemName) {
        super(itemName);
    }

    public TableDefinition constraint(FieldDefinition field){
        fields.add(field);
        return this;
    }

    public void remove(FieldDefinition field){
        for (int i = 0; i < fields.size(); ++i) {
            if(field.equals(fields.get(i))){
                fields.remove(i);
                break;
            }
        }
    }

    @Override
    public String createTable(TableCreateOptions options) {
        StringBuilder ret = new StringBuilder();
        ret.append("CREATE TABLE ");
        if(super.schema != null) {
            ret.append(super.schema).append('.');
        }
        ret.append(super.name).append(" (");
        for(FieldInfoCommand f : fields){
            ret.append(f.getDefinition()).append(" , ");
        }
        ret.deleteCharAt(ret.length()-1).append(')');
        return ret.toString();
    }

    @Override
    public String dropTable(TableDropOptions options) {
        StringBuilder ret = new StringBuilder();
        ret.append("DROP TABLE ");
        return ret.toString();
    }

    @Override
    public String alterTable(FieldInfoCommand field) {
        return null;
    }

    @Override
    public String renameTable(String name) {
        return null;
    }
}
