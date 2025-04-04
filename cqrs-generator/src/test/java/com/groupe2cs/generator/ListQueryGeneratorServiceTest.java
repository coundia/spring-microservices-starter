package com.groupe2cs.generator;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.config.GeneratorPropertiesTestConfig;
import com.groupe2cs.generator.model.EntityDefinition;
import com.groupe2cs.generator.service.ListQueryGeneratorService;
import com.groupe2cs.generator.service.QueryGeneratorService;
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
public class ListQueryGeneratorServiceTest {

    @Autowired
    ListQueryGeneratorService service;

    @Autowired
    GeneratorProperties generatorProperties;

    @Test
    void it_should_generate_list_query_file(@TempDir Path tempDir) throws Exception {
        Path templatesDir = tempDir.resolve("templates");
        Files.createDirectories(templatesDir);
        Files.writeString(
                templatesDir.resolve("list-query.mustache"),
                "package {{package}};\n\npublic class List{{name}}Query {\n    private long page;\n    private long limit;\n}"
        );

        EntityDefinition definition = EntityDefinition.fromClass(MockEntity.class);

        service.generate(definition, tempDir.toString());

        File generated = tempDir
                .resolve(generatorProperties.getQueryPackage() + "/ListMockEntityQuery.java")
                .toFile();

        assertThat(generated).exists();

        String content = Files.readString(generated.toPath());
        assertThat(content).contains("public class ListMockEntityQuery");
        assertThat(content).contains("private long page");
        assertThat(content).contains("private long limit");
    }

    static class MockEntity {
        private String id;
        private String name;
    }
}
