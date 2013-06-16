
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.Pair;

public class MapPosition extends Component {
    public Pair position;

    public MapPosition(int x, int y) {
        position = new Pair(x, y);
    }

    public MapPosition() {
        this(0, 0);
    }

    public MapPosition(Pair target) {
        position = target;
    }

    public Pair getPosition() {
        return position;
    }

    public void setPosition(Pair position) {
        this.position = position;
    }
}
