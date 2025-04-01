package com.groupe2cs.generator.service;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.model.EntityDefinition;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandGeneratorService {

    private final TemplateEngine templateEngine;
    private final FileWriterService fileWriterService;
    private final GeneratorProperties generatorProperties;


    public CommandGeneratorService(
            TemplateEngine templateEngine,
            FileWriterService fileWriterService,
            GeneratorProperties generatorProperties
    ) {
        this.templateEngine = templateEngine;
        this.fileWriterService = fileWriterService;
        this.generatorProperties = generatorProperties;
    }

    public void generate(EntityDefinition definition, String outputDir) {

        outputDir = outputDir +"/" +  this.generatorProperties.getCommandPackage();

        Map<String, Object> context = new HashMap<>(definition.toMap());
        context.put("package", Utils.getPackage(outputDir));

        String content = templateEngine.render("command.mustache", context);

        fileWriterService.write(outputDir, definition.getName() + "Command.java", content);
    }
}
