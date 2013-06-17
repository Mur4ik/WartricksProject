
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.Pair;

public class MapPosition extends Component {
    private Pair position;

    public MapPosition(int x, int y) {
        this.setPosition(new Pair(x, y));
    }

    public MapPosition() {
        this(0, 0);
    }

    public MapPosition(Pair target) {
        this.setPosition(target);
    }

    public Pair getPosition() {
        return position;
    }

    public void setPosition(Pair position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }
}
