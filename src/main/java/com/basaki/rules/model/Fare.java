package com.basaki.rules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fare {

    private Long nightSurcharge;

    private Long rideFare;

    public Long getTotalFare() {
        return nightSurcharge + rideFare;
    }
}
