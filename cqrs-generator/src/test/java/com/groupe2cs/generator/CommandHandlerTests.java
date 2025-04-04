package com.groupe2cs.generator;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.config.GeneratorPropertiesTestConfig;
import com.groupe2cs.generator.model.EntityDefinition;
import com.groupe2cs.generator.service.HandlerGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = {GeneratorPropertiesTestConfig.class})
public class CommandHandlerTests {

    @Autowired
    HandlerGeneratorService service;

    @Autowired
    GeneratorProperties generatorProperties;

    @Test
    void it_should_generate_command_handler_file(@TempDir Path tempDir) throws Exception {
        Path templatesDir = tempDir.resolve("templates");
        Files.createDirectories(templatesDir);
        Files.writeString(
                templatesDir.resolve("createCommandHandler.mustache"),
                "package test;\n\npublic class Create{{name}}CommandHandler {\n    public void handle(Create{{name}}Command command) {}\n}"
        );

        EntityDefinition definition = EntityDefinition.fromClass(MockEntity.class);
        service.generate(definition, tempDir.toString());

        File generated = tempDir.resolve(generatorProperties.getApplicationPackage() + "/CreateMockEntityCommandHandler.java").toFile();
        assertThat(generated).exists();

        String content = Files.readString(generated.toPath());
        assertThat(content).contains("public class CreateMockEntityCommandHandler");
        assertThat(content).contains("void handle(CreateMockEntityCommand");
    }
}
