
package com.wartricks.logic;

import java.util.Observable;

import com.badlogic.gdx.utils.Array;
import com.wartricks.custom.Pair;
import com.wartricks.utils.Constants.Players;

public class StateMachine extends Observable {
    public enum GameState {
        CHOOSING_CHARACTER, CHOOSING_SKILL, CHOOSING_TARGET, CHOOSING_CONFIRM, PLAYER_FINISHED, BEGIN_TURN, END_TURN
    }

    private GameState currentState;

    private Players activePlayer;

    private int selectedCreature, selectedSkill;

    private Pair selectedHex;

    private Array<Integer> selectedIds;

    public StateMachine() {
        super();
        currentState = GameState.CHOOSING_CHARACTER;
        activePlayer = Players.ONE;
        selectedCreature = -1;
        selectedSkill = -1;
        selectedHex = null;
        selectedIds = new Array<Integer>();
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
        activePlayer = player;
        this.setChanged();
        this.notifyObservers(activePlayer);
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

    public Array<Integer> getSelectedIds() {
        return selectedIds;
    }

    public boolean clearSelectedIds() {
        selectedIds.clear();
        return true;
    }
}
