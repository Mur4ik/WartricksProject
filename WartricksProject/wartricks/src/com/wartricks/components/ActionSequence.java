
package com.wartricks.components;

import java.util.LinkedList;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class ActionSequence extends Component {
    public LinkedList<Action> path;

    public Color pathColor;

    public ActionSequence(Action move, Color color) {
        path = new LinkedList<Action>();
        path.addLast(move);
        pathColor = color;
    }

    public ActionSequence(Action move) {
        this(move, Color.BLACK);
    }

    public ActionSequence(Color color) {
        path = new LinkedList<Action>();
        pathColor = color;
    }

    public ActionSequence() {
        this(Color.BLACK);
    }
}
