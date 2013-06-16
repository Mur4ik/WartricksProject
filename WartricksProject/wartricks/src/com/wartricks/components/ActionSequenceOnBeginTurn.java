
package com.wartricks.components;

import java.util.LinkedList;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class ActionSequenceOnBeginTurn extends Component {
    public LinkedList<Action> onBeginTurnActions;

    public Color pathColor;

    public ActionSequenceOnBeginTurn(Action move, Color color) {
        onBeginTurnActions = new LinkedList<Action>();
        onBeginTurnActions.addLast(move);
        pathColor = color;
    }

    public ActionSequenceOnBeginTurn(Action move) {
        this(move, Color.BLACK);
    }

    public ActionSequenceOnBeginTurn(Color color) {
        onBeginTurnActions = new LinkedList<Action>();
        pathColor = color;
    }

    public ActionSequenceOnBeginTurn() {
        this(Color.BLACK);
    }
}
