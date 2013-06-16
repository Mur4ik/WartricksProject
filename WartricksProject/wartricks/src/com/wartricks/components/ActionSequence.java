
package com.wartricks.components;

import java.util.LinkedList;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class ActionSequence extends Component {
    public LinkedList<Action> onCastActions;

    public LinkedList<Action> onBeginTurnActions;

    public LinkedList<Action> onEndTurnActions;

    public Color pathColor;

    public ActionSequence(Action move, Color color) {
        onCastActions = new LinkedList<Action>();
        onBeginTurnActions = new LinkedList<Action>();
        onEndTurnActions = new LinkedList<Action>();
        onCastActions.addLast(move);
        pathColor = color;
    }

    public ActionSequence(Action move) {
        this(move, Color.BLACK);
    }

    public ActionSequence(Color color) {
        onCastActions = new LinkedList<Action>();
        onBeginTurnActions = new LinkedList<Action>();
        onEndTurnActions = new LinkedList<Action>();
        pathColor = color;
    }

    public ActionSequence() {
        this(Color.BLACK);
    }
}
