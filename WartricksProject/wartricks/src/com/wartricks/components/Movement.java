
package com.wartricks.components;

import com.artemis.Component;

public class Movement extends Component {
    public float originX;

    public float originY;

    public float destinationX;

    public float destinationY;

    public Movement(float x0, float y0, float tx, float ty) {
        originX = x0;
        originY = y0;
        destinationX = tx;
        destinationY = ty;
    }
}
