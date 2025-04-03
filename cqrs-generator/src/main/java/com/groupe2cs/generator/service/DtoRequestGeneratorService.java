package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.model.EntityDefinition;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DtoRequestGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties generatorProperties;

    public DtoRequestGeneratorService(TemplateEngine templateEngine, FileWriterService fileWriterService, GeneratorProperties generatorProperties) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.generatorProperties = generatorProperties;
    }

    public void generate(EntityDefinition definition, String baseDir) {
        Map<String, Object> context = new HashMap<>(definition.toMap());

        String outputDir = baseDir + "/" + generatorProperties.getDtoPackage();
        context.put("package", Utils.getPackage(outputDir));

        var fields = definition.getFields();
        context.put("fields", FieldTransformer.transform(fields, definition.getName()));

        Set<String> imports = new LinkedHashSet<>();
        imports.add(Utils.getPackage(baseDir + "/" + generatorProperties.getVoPackage()) + ".*");
        context.put("imports", imports);

        String content = templateEngine.render("dtoRequest.mustache", context);
        fileWriterService.write(outputDir, definition.getName() + "Request.java", content);
    }
}
