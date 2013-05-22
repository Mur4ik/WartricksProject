
package com.wartricks.components;

import com.artemis.Component;

public class Bounds extends Component {
    public float radius;

    public Bounds(float radius) {
        this.radius = radius;
    }

    public Bounds() {
        this(0);
    }
}
