
package com.wartricks.components;

import com.artemis.Component;

public class Movement extends Component {
    public int originX;

    public int originY;

    public int destinationX;

    public int destinationY;

    public Movement(int x0, int y0, int tx, int ty) {
        originX = x0;
        originY = y0;
        destinationX = tx;
        destinationY = ty;
    }
}
