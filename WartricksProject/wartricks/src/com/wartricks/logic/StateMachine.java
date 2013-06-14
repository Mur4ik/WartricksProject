
package com.wartricks.logic;

import java.util.Observable;

public class StateMachine extends Observable {
    enum GameState {
        CHOOSING_CHARACTER, CHOOSING_SKILL, CHOOSING_TARGET, CHOOSING_CONFIRM, RESOLVING_ACTIONS
    }

    private GameState currentState;

    public StateMachine() {
        super();
        currentState = GameState.CHOOSING_CHARACTER;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState state) {
        currentState = state;
        this.setChanged();
        this.notifyObservers(currentState);
    }
}
