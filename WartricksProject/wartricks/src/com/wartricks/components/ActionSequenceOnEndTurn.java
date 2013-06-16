
package com.wartricks.components;

import java.util.LinkedList;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class ActionSequenceOnEndTurn extends Component {
    public LinkedList<Action> onEndTurnActions;

    public Color pathColor;

    public ActionSequenceOnEndTurn(Action move, Color color) {
        onEndTurnActions = new LinkedList<Action>();
        onEndTurnActions.addLast(move);
        pathColor = color;
    }

    public ActionSequenceOnEndTurn(Action move) {
        this(move, Color.BLACK);
    }

    public ActionSequenceOnEndTurn(Color color) {
        onEndTurnActions = new LinkedList<Action>();
        pathColor = color;
    }

    public ActionSequenceOnEndTurn() {
        this(Color.BLACK);
    }
}
