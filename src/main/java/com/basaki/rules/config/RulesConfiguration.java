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


    @Bean
    public KieContainer getContainer() {
        KieServices services = KieServices.Factory.get();

        KieFileSystem fileSystem = services.newKieFileSystem();
        fileSystem.write(ResourceFactory.newClassPathResource(RULES_TAXI_FARE_RULE));
        fileSystem.write(ResourceFactory.newClassPathResource(HELLO_WORLD_RULE));
        KieBuilder builder = services.newKieBuilder(fileSystem);
        builder.buildAll();
        KieModule kieModule = builder.getKieModule();

        return services.newKieContainer(kieModule.getReleaseId());
    }
}

/*
public class HelloWorldExample {

    public static final void main(final String[] args) {
        // From the kie services, a container is created from the classpath
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();

        execute( ks, kc );
    }

    public static void execute( KieServices ks, KieContainer kc ) {
        // From the container, a session is created based on
        // its definition and configuration in the META-INF/kmodule.xml file
        KieSession ksession = kc.newKieSession("HelloWorldKS");

        // Once the session is created, the application can interact with it
        // In this case it is setting a global as defined in the
        // org/drools/examples/helloworld/HelloWorld.drl file
        ksession.setGlobal( "list", new ArrayList<Object>() );

        // The application can also setup listeners
        ksession.addEventListener( new DebugAgendaEventListener() );
        ksession.addEventListener( new DebugRuleRuntimeEventListener() );

        // Set up a file based audit logger
        KieRuntimeLogger logger = ks.getLoggers().newFileLogger( ksession, "./helloworld" );

        // To set up a ThreadedFileLogger, so that the audit view reflects events whilst debugging,
        // uncomment the next line
        // KieRuntimeLogger logger = ks.getLoggers().newThreadedFileLogger( ksession, "./helloworld", 1000 );

        // The application can insert facts into the session
        final Message message = new Message();
        message.setMessage( "Hello World" );
        message.setStatus( Message.HELLO );
        ksession.insert( message );

        // and fire the rules
        ksession.fireAllRules();

        // Close logger
        logger.close();

        // and then dispose the session
        ksession.dispose();
    }

    public static class Message {
        public static final int HELLO   = 0;
        public static final int GOODBYE = 1;

        private String          message;

        private int             status;

        public Message() {

        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(final int status) {
            this.status = status;
        }

        public static Message doSomething(Message message) {
            return message;
        }

        public boolean isSomething(String msg,
                                   List<Object> list) {
            list.add( this );
            return this.message.equals( msg );
        }
    }

}
 */