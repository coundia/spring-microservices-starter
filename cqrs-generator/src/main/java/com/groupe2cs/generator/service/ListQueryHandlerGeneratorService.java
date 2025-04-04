package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.model.EntityDefinition;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ListQueryHandlerGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties generatorProperties;

    public ListQueryHandlerGeneratorService(
            TemplateEngine templateEngine,
            FileWriterService fileWriterService,
            GeneratorProperties generatorProperties
    ) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.generatorProperties = generatorProperties;
    }

    public void generate(EntityDefinition definition, String baseDir) {
        Map<String, Object> context = new HashMap<>();

        String outputDir = baseDir + "/" + generatorProperties.getQueryHandlerPackage();
        context.put("package", Utils.getPackage(outputDir));
        context.put("entity", definition.getName());
        context.put("entityLower", definition.getName().toLowerCase());

        Set<String> imports = new LinkedHashSet<>();
        imports.add("org.axonframework.queryhandling.QueryHandler");
        imports.add("org.springframework.stereotype.Component");
        imports.add("reactor.core.publisher.Mono");

        imports.add(Utils.getPackage(baseDir + "/" + generatorProperties.getDtoPackage()) + ".*");
        imports.add(Utils.getPackage(baseDir + "/" + generatorProperties.getRepositoryPackage()) + ".*");
        imports.add(Utils.getPackage(baseDir + "/" + generatorProperties.getQueryPackage()) + ".*");

        context.put("imports", imports);

        String content = templateEngine.render("list-query-handler.mustache", context);
        fileWriterService.write(outputDir, "List" + definition.getName() + "QueryHandler.java", content);
    }
}
