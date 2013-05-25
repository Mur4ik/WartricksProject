
package com.wartricks.components;

import java.util.LinkedList;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

public class Path extends Component {
    public LinkedList<Move> path;

    public Color pathColor;

    public Path(Move move, Color color) {
        path = new LinkedList<Move>();
        path.addLast(move);
        pathColor = color;
    }

    public Path(Move move) {
        this(move, Color.BLACK);
    }

    public Path(Color color) {
        path = new LinkedList<Move>();
        pathColor = color;
    }

    public Path() {
        this(Color.BLACK);
    }
}
