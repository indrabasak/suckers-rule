package com.basaki.rules.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    public static final int HELLO   = 0;

    public static final int GOODBYE = 1;

    private String          message;

    private int             status;

    public static Message doSomething(Message message) {
        return message;
    }

    public boolean isSomething(String msg, List<Object> list) {
        list.add( this );
        return this.message.equals( msg );
    }
}
