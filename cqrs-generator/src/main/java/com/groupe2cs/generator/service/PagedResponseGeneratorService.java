package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.model.EntityDefinition;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PagedResponseGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties properties;

    public PagedResponseGeneratorService(TemplateEngine templateEngine, FileWriterService fileWriterService, GeneratorProperties properties) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.properties = properties;
    }

    public void generate(EntityDefinition definition, String baseDir) {
        Map<String, Object> context = new HashMap<>();
        String outputDir = baseDir + "/" + properties.getDtoPackage();
        context.put("package", Utils.getPackage(outputDir));
        context.put("name", definition.getName());

        String content = templateEngine.render("pagedResponse.mustache", context);
        fileWriterService.write(outputDir, definition.getName()+"PagedResponse.java", content);
    }
}
