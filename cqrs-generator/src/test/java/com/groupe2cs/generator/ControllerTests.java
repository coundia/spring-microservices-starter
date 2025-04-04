package com.groupe2cs.generator;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.config.GeneratorPropertiesTestConfig;
import com.groupe2cs.generator.model.EntityDefinition;
import com.groupe2cs.generator.service.ControllerGeneratorService;
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
public class ControllerTests {

    @Autowired
    ControllerGeneratorService service;

    @Autowired
    GeneratorProperties generatorProperties;

    @Test
    void it_should_generate_controller_file(@TempDir Path tempDir) throws Exception {
        Path templatesDir = tempDir.resolve("templates");
        Files.createDirectories(templatesDir);
        Files.writeString(
                templatesDir.resolve("api.mustache"),
                "package test;\n\n@RestController\n@RequestMapping(\"/{{lowerName}}s\")\npublic class {{name}}Controller {\n}"
        );

        EntityDefinition definition = EntityDefinition.fromClass(MockEntity.class);
        service.generate(definition, tempDir.toString());

        File generated = tempDir.resolve(generatorProperties.getControllerPackage() + "/MockEntityController.java").toFile();
        assertThat(generated).exists();

        String content = Files.readString(generated.toPath());
        assertThat(content).contains("public class MockEntityController");
        assertThat(content).contains("@RestController");
    }
}
