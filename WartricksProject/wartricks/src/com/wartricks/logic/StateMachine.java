
package com.wartricks.logic;

import java.util.Observable;

import com.wartricks.utils.Constants.Players;

public class StateMachine extends Observable {
    enum GameState {
        CHOOSING_CHARACTER, CHOOSING_SKILL, CHOOSING_TARGET, CHOOSING_CONFIRM, RESOLVING_ACTIONS
    }

    private GameState currentState;

    private Players activePlayer;

    public StateMachine() {
        super();
        currentState = GameState.CHOOSING_CHARACTER;
        activePlayer = Players.ONE;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState state) {
        currentState = state;
        this.setChanged();
        this.notifyObservers(currentState);
    }

    public void setActivePlayer(Players player) {
        // To maintain the finite automata consistent we reset it
        currentState = GameState.CHOOSING_CHARACTER;
        activePlayer = player;
        this.setChanged();
        this.notifyObservers(currentState);
    }
}
