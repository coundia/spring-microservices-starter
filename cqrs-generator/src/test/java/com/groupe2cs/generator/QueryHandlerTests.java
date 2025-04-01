package com.groupe2cs.generator;

import com.groupe2cs.generator.config.GeneratorProperties;
import com.groupe2cs.generator.config.GeneratorPropertiesTestConfig;
import com.groupe2cs.generator.model.EntityDefinition;
import com.groupe2cs.generator.service.QueryHandlerGeneratorService;
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
public class QueryHandlerTests {

    @Autowired
    QueryHandlerGeneratorService service;

    @Autowired
    GeneratorProperties generatorProperties;

    @Test
    void it_should_generate_query_handler_file(@TempDir Path tempDir) throws Exception {
        Path templatesDir = tempDir.resolve("templates");
        Files.createDirectories(templatesDir);
        Files.writeString(
                templatesDir.resolve("queryHandler.mustache"),
                "package test;\n\npublic class {{name}}QueryHandler {\n    public {{name}}Response handle(Get{{name}}ByIdQuery query) { return null; }\n    public java.util.List<{{name}}Response> handle(GetAll{{name}}Query query) { return java.util.List.of(); }\n}"
        );

        EntityDefinition definition = EntityDefinition.fromClass(MockEntity.class);
        service.generate(definition, tempDir.toString());

        File file = tempDir.resolve(generatorProperties.getQueryHandlerPackage() + "/MockEntityQueryHandler.java").toFile();
        assertThat(file).exists();

        String content = Files.readString(file.toPath());
        assertThat(content).contains("class MockEntityQueryHandler");
        assertThat(content).contains("handle(GetMockEntityByIdQuery query)");
        assertThat(content).contains("handle(GetAllMockEntityQuery query)");
    }
}
