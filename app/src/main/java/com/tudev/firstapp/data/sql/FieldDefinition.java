package com.tudev.firstapp.data.sql;

import java.util.EnumSet;

/**
 * Created by arseniy on 06.08.16.
 */

public class FieldDefinition extends DatabaseNamedItem implements IFieldDefinition {

    private FieldType fieldType;
    private EnumSet<FieldModifier> modifiers = EnumSet.noneOf(FieldModifier.class);

    public FieldDefinition(String itemName, FieldType fieldType) {
        super(itemName);
        this.fieldType = fieldType;
        this.modifiers = EnumSet.noneOf(FieldModifier.class);
    }

    public FieldDefinition(String name, FieldType type, EnumSet<FieldModifier> modifiers){
        super(name);
        fieldType = type;
        this.modifiers = modifiers;
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return super.name.hashCode();
    }

    @Override
    public String getDefinition() {
        StringBuilder ret = new StringBuilder();
        if(super.schema != null) ret.append(super.schema).append('.');
        ret.append(super.name).append(' ');
        ret.append(fieldType.name()).append(' ');
        for(FieldModifier mod : modifiers){
            ret.append(mod.name().replace('_', ' ')).append(' ');
        }
        return ret.toString();
    }
}
