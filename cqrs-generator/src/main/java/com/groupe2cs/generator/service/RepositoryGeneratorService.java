package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.model.EntityDefinition;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RepositoryGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties generatorProperties;

    public RepositoryGeneratorService(TemplateEngine templateEngine, FileWriterService fileWriterService, GeneratorProperties generatorProperties) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.generatorProperties = generatorProperties;
    }

    public void generate(EntityDefinition definition, String baseDir) {
        Map<String, Object> context = new HashMap<>(definition.toMap());

        String outputDir = baseDir + "/" + generatorProperties.getRepositoryPackage();
        context.put("package", Utils.getPackage(outputDir));

        Set<String> imports = new LinkedHashSet<>();
        imports.add(Utils.getPackage(baseDir + "/" + generatorProperties.getEntityPackage()) + "." + definition.getName());

        context.put("imports", imports);

        context.put("nameLower", definition.getName().toLowerCase());
        context.put("fields", definition.getFields().stream()
                .filter(f -> !f.getName().equalsIgnoreCase("id"))
                .map(f -> Map.of("name", f.getName(), "type", f.getType()))
                .toList());

        String content = templateEngine.render("repository.mustache", context);
        fileWriterService.write(outputDir, definition.getName() + "Repository.java", content);
    }
}
