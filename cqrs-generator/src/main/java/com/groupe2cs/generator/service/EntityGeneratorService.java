package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.model.EntityDefinition;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EntityGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties generatorProperties;

    public EntityGeneratorService(TemplateEngine templateEngine, FileWriterService fileWriterService, GeneratorProperties generatorProperties) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.generatorProperties = generatorProperties;
    }

    public void generate(EntityDefinition definition, String baseDir) {
        Map<String, Object> context = new HashMap<>(definition.toMap());

        String outputDir = baseDir + "/" + generatorProperties.getEntityPackage();
        context.put("package", Utils.getPackage(outputDir));
        context.put("table", definition.getName().toLowerCase());

        var fields = definition.getFields();

        fields = fields.stream().filter(
                f-> !f.getName().equalsIgnoreCase("id")
        ).toList();

        context.put("fields", fields);

        Set<String> imports = new LinkedHashSet<>();
        imports.add("org.springframework.data.annotation.Id");
        imports.add("org.springframework.data.relational.core.mapping.Table");
        imports.add(Utils.getPackage(baseDir + "/" + generatorProperties.getVoPackage()) + ".*");
        context.put("imports", imports);

        String content = templateEngine.render("entity.mustache", context);
        fileWriterService.write(outputDir, definition.getName() + ".java", content);
    }
}
