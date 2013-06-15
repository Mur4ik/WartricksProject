
package com.wartricks.logic;

import java.util.Observable;

import com.wartricks.custom.Pair;
import com.wartricks.utils.Constants.Players;

public class StateMachine extends Observable {
    public enum GameState {
        CHOOSING_CHARACTER, CHOOSING_SKILL, CHOOSING_TARGET, CHOOSING_CONFIRM, RESOLVING_ACTIONS
    }

    private GameState currentState;

    private Players activePlayer;

    private int selectedCreature, selectedSkill;

    private Pair selectedHex;

    public StateMachine() {
        super();
        currentState = GameState.CHOOSING_CHARACTER;
        activePlayer = Players.ONE;
        selectedCreature = -1;
        selectedSkill = -1;
        selectedHex = null;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public Players getActivePlayer() {
        return activePlayer;
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

    public int getSelectedCreature() {
        return selectedCreature;
    }

    public void setSelectedCreature(int selectedCreature) {
        this.selectedCreature = selectedCreature;
    }

    public int getSelectedSkill() {
        return selectedSkill;
    }

    public void setSelectedSkill(int selectedSkill) {
        this.selectedSkill = selectedSkill;
    }

    public Pair getSelectedHex() {
        return selectedHex;
    }

    public void setSelectedHex(Pair selectedHex) {
        this.selectedHex = selectedHex;
    }
}
