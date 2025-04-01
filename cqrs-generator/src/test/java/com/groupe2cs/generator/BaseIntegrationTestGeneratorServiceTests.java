package com.groupe2cs.generator;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.config.GeneratorPropertiesTestConfig;
import com.groupe2cs.generator.service.BaseIntegrationTestGeneratorService;
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
public class BaseIntegrationTestGeneratorServiceTests {

    @Autowired
    BaseIntegrationTestGeneratorService service;

    @Autowired
    GeneratorProperties generatorProperties;

    @Test
    void it_should_generate_base_integration_test_file(@TempDir Path tempDir) throws Exception {
        Path templatesDir = tempDir.resolve("templates");
        Files.createDirectories(templatesDir);

        Files.writeString(
                templatesDir.resolve("baseIntegrationTest.mustache"),
                "package {{package}};\n\nimport com.example.products.infrastructure.ProductRepository;\n\npublic class BaseIntegrationTests {\n@Autowired\npublic ProductRepository productRepository;\n}"
        );

        service.generate();

        Path filePath = Path.of(generatorProperties.getTestPackage(), "BaseIntegrationTests.java");
        File file = filePath.toFile();

        assertThat(file).exists();

    }
}
