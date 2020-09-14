package com.basaki.rules.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RulesConfiguration {

    private static final String RULES_TAXI_FARE_RULE = "rules/TaxiFareRule.drl";

    private static final String HELLO_WORLD_RULE = "rules/HelloWorld.drl";

    private static final String STATE_USING_SALIENCE_RULE = "rules/StateExampleUsingSalience.drl";

    @Bean
    public KieContainer getContainer() {
        KieServices services = KieServices.Factory.get();

        KieFileSystem fileSystem = services.newKieFileSystem();
        fileSystem.write(ResourceFactory.newClassPathResource(RULES_TAXI_FARE_RULE));
        fileSystem.write(ResourceFactory.newClassPathResource(HELLO_WORLD_RULE));
        fileSystem.write(ResourceFactory.newClassPathResource(STATE_USING_SALIENCE_RULE));
        KieBuilder builder = services.newKieBuilder(fileSystem);
        builder.buildAll();
        KieModule kieModule = builder.getKieModule();

        return services.newKieContainer(kieModule.getReleaseId());
    }
}