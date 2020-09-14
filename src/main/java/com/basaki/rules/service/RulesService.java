package com.basaki.rules.service;

import com.basaki.rules.model.Fare;
import com.basaki.rules.model.Message;
import com.basaki.rules.model.State;
import com.basaki.rules.model.TaxiRide;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

    @Autowired
    private KieContainer container;

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

    public void changeStateUsingSalience(State state) {
        KieSession session = container.newKieSession();
        final State a = new State( "A" );
        final State b = new State( "B" );
        final State c = new State( "C" );
        final State d = new State( "D" );

        session.insert( a );
        session.insert( b );
        session.insert( c );
        session.insert( d );

        //session.insert(state);
        session.fireAllRules();
        session.dispose();
    }
}
