
package com.wartricks.components;

import com.artemis.Component;

public class MapPosition extends Component {
    public float x, y;

    public MapPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MapPosition() {
        this(0, 0);
    }
}
