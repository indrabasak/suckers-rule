package com.basaki.rules.model;

import lombok.Getter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class State {

    public enum NAME { A, B, C, D }

    public enum STATE_TYPE {NOTRUN, FINISHED}

    public static final int NOTRUN = 0;

    public static final int FINISHED = 1;

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    @Getter
    private NAME name;

    @Getter
    private STATE_TYPE state;

    public State(final NAME name) {
        this.name = name;
        this.state = STATE_TYPE.NOTRUN;
    }

    public void setState(final STATE_TYPE newState) {
        STATE_TYPE oldState = this.state;
        this.state = newState;
        this.changes.firePropertyChange("state", oldState, newState);
    }

    public boolean inState(final NAME name,
                           final STATE_TYPE state) {
        return this.name.equals(name) && this.state == state;
    }

    public String toString() {
        switch (this.state) {
            case NOTRUN:
                return this.name + "[" + "NOTRUN" + "]";
            case FINISHED:
            default:
                return this.name + "[" + "FINISHED" + "]";
        }
    }

    public void addPropertyChangeListener(final PropertyChangeListener l) {
        this.changes.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(final PropertyChangeListener l) {
        this.changes.removePropertyChangeListener(l);
    }
}
