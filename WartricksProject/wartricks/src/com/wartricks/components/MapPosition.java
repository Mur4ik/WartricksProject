
package com.wartricks.components;

import com.artemis.Component;

public class MapPosition extends Component {
    public int x, y;

    public MapPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MapPosition() {
        this(0, 0);
    }
}
