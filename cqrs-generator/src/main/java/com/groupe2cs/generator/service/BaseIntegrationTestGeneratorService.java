package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BaseIntegrationTestGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties generatorProperties;

    public BaseIntegrationTestGeneratorService(TemplateEngine templateEngine, FileWriterService fileWriterService, GeneratorProperties generatorProperties) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.generatorProperties = generatorProperties;
    }

    public void generate() {
        Map<String, Object> context = new HashMap<>();

        String outputDir = generatorProperties.getTestPackage();
        context.put("package", Utils.getPackage(outputDir));

        String content = templateEngine.render("baseIntegrationTest.mustache", context);
        fileWriterService.write(outputDir, "BaseIntegrationTests.java", content);
    }
}
