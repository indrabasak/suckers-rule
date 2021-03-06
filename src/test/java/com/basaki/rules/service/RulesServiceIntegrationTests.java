package com.basaki.rules.service;


import com.basaki.rules.model.Message;
import com.basaki.rules.model.State;
import com.basaki.rules.model.TaxiRide;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@code RulesServiceIntegrationTests} represents integration tests for
 * {@code RulesService}.
 * <p/>
 *
 * @author Indra Basak
 * @since 09/09/20
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RulesServiceIntegrationTests {

    @Autowired
    private RulesService service;

    @ParameterizedTest
    @MethodSource
    void testCalculateFare(boolean nightSurcharge, long distanceInMile,
                           long expectedCharge) {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(nightSurcharge);
        taxiRide.setDistanceInMile(distanceInMile);

        Long totalCharge = service.calculateFare(taxiRide);
        assertNotNull(totalCharge);
        assertEquals(expectedCharge, totalCharge);
    }

    static Stream<Arguments> testCalculateFare() {
        return Stream.of(
                Arguments.arguments(false, 9, 70),
                Arguments.arguments(true, 5, 100),
                Arguments.arguments(false, 50, 170),
                Arguments.arguments(true, 50, 250),
                Arguments.arguments(false, 100, 220),
                Arguments.arguments(true, 100, 350)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSayHello(Message.Status status, String text, String expected) {
        Message message = new Message();
        message.setStatus(status);
        message.setText(text);

        assertEquals(expected, service.sayHello(message));
    }

    static Stream<Arguments> testSayHello() {
        return Stream.of(
                Arguments.arguments(Message.Status.HELLO, "Do something!",
                        Message.Status.HELLO.getValue() + " Do something!"),
                Arguments.arguments(Message.Status.GOODBYE, "Do nothing!",
                        Message.Status.GOODBYE.getValue() + " Do nothing!")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSayHelloWithFilter(Message.Status status, String text, String expected, String[] rules) {
        Message message = new Message();
        message.setStatus(status);
        message.setText(text);

        assertEquals(expected, service.sayHelloWithFilter(message, rules));
    }

    static Stream<Arguments> testSayHelloWithFilter() {
        return Stream.of(
                Arguments.arguments(Message.Status.HELLO, "Do something!",
                        Message.Status.HELLO.getValue() + " Do something!", new String[] {"Hello World"}),
                Arguments.arguments(Message.Status.GOODBYE, "Do nothing!",
                        "Do nothing!", new String[] {"Hello World"}),
                Arguments.arguments(Message.Status.GOODBYE, "Do nothing!",
                        Message.Status.GOODBYE.getValue() + " Do nothing!", new String[] {"Good Bye"})
        );
    }

    @Test
    void testChangeStateUsingSalience() {
        State[] states = {new State(State.NAME.A), new State(State.NAME.B),
                new State(State.NAME.C), new State(State.NAME.D)};
        service.changeStateUsingSalience(states);
        Arrays.stream(states).forEach(s -> assertEquals(State.STATE_TYPE.FINISHED, s.getState()));
    }

    @ParameterizedTest
    @MethodSource
    void testFibonacci(int sequence, long expected) {
        assertEquals(expected, service.fibonacci(sequence));
    }

    static Stream<Arguments> testFibonacci() {
        return Stream.of(
                Arguments.arguments(5, 5),
                Arguments.arguments(7, 13),
                Arguments.arguments(13, 233)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAgendaEvent(int code, String text, boolean fire, String expectedRule) {
        final DefaultAgendaEventListener listener = new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                super.afterMatchFired(event);
                if (fire) {
                    assertEquals(expectedRule, event.getMatch().getRule().getName());
                }
            }
        };

        service.agendaEvent(code, text, listener);
    }

    static Stream<Arguments> testAgendaEvent() {
        return Stream.of(
                Arguments.arguments(20, "agenda example rule", true, "agendatest"),
                Arguments.arguments(30, "agenda example rule", false, null)
        );
    }
}
