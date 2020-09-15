package com.basaki.rules.controller;

import com.basaki.rules.model.Message;
import com.basaki.rules.model.TaxiRide;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@code RulesControllerFunctionalTests} represents functional tests for
 * {@code RulesController}.
 * <p/>
 *
 * @author Indra Basak
 * @since 09/15/20
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RulesControllerFunctionalTests {

    @Value("${local.server.port}")
    private Integer port;

    @Autowired
    @Qualifier("customObjectMapper")
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource
    void testCalculateFare(boolean nightSurcharge, long distanceInMile,
                           long expectedCharge) throws IOException {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(nightSurcharge);
        taxiRide.setDistanceInMile(distanceInMile);

        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .body(taxiRide)
                .post("/rules/fare");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());

        long actual = objectMapper.readValue(response.asInputStream(), Long.class);
        assertEquals(expectedCharge, actual);
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
    void testSayHello(Message.Status status, String text, String expected) throws IOException {
        Message message = new Message();
        message.setStatus(status);
        message.setText(text);

        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .body(message)
                .post("/rules/hello");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals(expected, response.getBody().asString());
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
    void testFibonacci(int sequence, long expected) throws IOException {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .get("/rules/fibonacci/" + sequence);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        long actual = objectMapper.readValue(response.asInputStream(), Long.class);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> testFibonacci() {
        return Stream.of(
                Arguments.arguments(5, 5),
                Arguments.arguments(7, 13),
                Arguments.arguments(13, 233)
        );
    }
}
