package com.basaki.rules.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    public enum Status {

        HELLO("Hello"), GOODBYE("Goodbye");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String text;

    private Status status;
}
