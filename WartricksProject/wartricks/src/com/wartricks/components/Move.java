
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.FloatPair;

public class Move extends Component {
    public FloatPair origin;

    public FloatPair destination;

    public Move(float x0, float y0, float tx, float ty) {
        origin = new FloatPair(x0, y0);
        destination = new FloatPair(tx, ty);
    }

    public Move(FloatPair originPar, FloatPair destinationPar) {
        origin = originPar;
        destination = destinationPar;
    }

    public Move() {
        origin = new FloatPair(0, 0);
        destination = new FloatPair(0, 0);
    }
}
