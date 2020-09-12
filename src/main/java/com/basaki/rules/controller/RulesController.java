package com.basaki.rules.controller;

import com.basaki.rules.model.Message;
import com.basaki.rules.model.TaxiRide;
import com.basaki.rules.service.RulesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code UdonController} for anonymizing and deanonymizing data.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/25/20
 */
@RestController
@Slf4j
public class RulesController {

    private final RulesService service;

    @Autowired
    public RulesController(RulesService service) {
        this.service = service;
    }

    @PostMapping(value = "/fare",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public long calculateFare(@RequestBody TaxiRide taxiRide) {
        return service.calculateFare(taxiRide);
    }

    @PostMapping(value = {"/hello"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public String sayHello(@RequestBody Message message) {
        return service.sayHello(message);
    }
}
