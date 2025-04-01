package com.groupe2cs.generator.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GeneratorPropertiesTestConfig {

    @Bean
    public GeneratorProperties generatorProperties() {
        GeneratorProperties props = new GeneratorProperties();
        props.setDomainPackage("domain");
        props.setApplicationPackage("application");
        props.setInfrastructurePackage("infrastructure");
        props.setPresentationPackage("presentation");

        props.setVoPackage("domain/valueObject");
        props.setCommandPackage("command");
        props.setEventPackage("event");
        props.setRepositoryPackage("repository");
        props.setServicePackage("service");
        props.setControllerPackage("api");
        props.setDtoPackage("dto");
        props.setMapperPackage("mapper");
        props.setTestPackage("test");

        return props;
    }
}
