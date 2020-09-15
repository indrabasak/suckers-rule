package com.basaki.rules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Fibonacci {

    private int sequence;

    @Setter
    private long value;

    public Fibonacci(final int sequence) {
        this.sequence = sequence;
        this.value = -1;
    }


    public String toString() {
        return "Fibonacci(" + this.sequence + "/" + this.value + ")";
    }
}
