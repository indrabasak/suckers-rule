package com.basaki.rules.service;


import com.basaki.rules.model.TaxiRide;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RulesServiceIntegrationTests {

    @Autowired
    private RulesService service;

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

    static Stream<Arguments> testSayHello() {
        return Stream.of(
                Arguments.arguments(0),
                Arguments.arguments(1)
        );
    }

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

    @ParameterizedTest
    @MethodSource
    void testSayHello(int status) {
        String msg = service.sayHello(status);
        System.out.println(msg);
    }
}
