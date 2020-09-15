package com.basaki.rules.service;

import com.basaki.rules.model.Fare;
import com.basaki.rules.model.Fibonacci;
import com.basaki.rules.model.Message;
import com.basaki.rules.model.State;
import com.basaki.rules.model.TaxiRide;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * {@code RulesService} is responsible for exposing the rules as a service.
 * <p>
 *
 * @author Indra Basak
 * @since 09/09/20
 */
@Service
public class RulesService {

    private KieContainer container;

    @Autowired
    public RulesService(KieContainer container) {
        this.container = container;
    }

    public Long calculateFare(TaxiRide taxiRide) {
        KieSession session = container.newKieSession();
        Fare rideFare = new Fare();
        session.setGlobal("rideFare", rideFare);
        session.insert(taxiRide);
        session.fireAllRules();
        session.dispose();

        return rideFare.getTotalFare();
    }

    public String sayHello(Message message) {
        KieSession session = container.newKieSession();
        session.insert(message);
        session.fireAllRules();
        session.dispose();

        return message.getText();
    }

    public void changeStateUsingSalience(State... states) {
        KieSession session = container.newKieSession();
        Arrays.stream(states).forEach(session::insert);
        session.fireAllRules();
        session.dispose();
    }

    public long fibonacci(int sequence) {
        KieSession session = container.newKieSession();
        Fibonacci fibonacci = new Fibonacci(sequence);
        session.insert(fibonacci);
        session.fireAllRules();
        session.dispose();

        return fibonacci.getValue();
    }
}
