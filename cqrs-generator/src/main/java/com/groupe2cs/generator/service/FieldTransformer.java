package com.groupe2cs.generator.service;

import com.groupe2cs.generator.model.FieldDefinition;

import java.util.*;

public class FieldTransformer {

    public static List<Map<String, Object>> transform(List<FieldDefinition> fields, String entityName) {
        List<Map<String, Object>> result = new ArrayList<>();


        for (int i = 0; i < fields.size(); i++) {
            var field = fields.get(i);
            Map<String, Object> f = new HashMap<>();
            f.put("name", field.getName());
            f.put("nameCapitalized", Utils.capitalize(field.getName()));
            f.put("type", entityName + Utils.capitalize(field.getName()));
            f.put("isId", field.getName().equalsIgnoreCase("id"));
            f.put("last", i == fields.size() - 1);
            result.add(f);
        }

        return result;
    }

}
