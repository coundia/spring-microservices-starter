package com.groupe2cs.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDefinition implements Serializable {
    private String type;
    private String name;

    public boolean isDate() {

        return type.equalsIgnoreCase("date") ||
                type.equalsIgnoreCase("datetime")
                ;
    }
}
