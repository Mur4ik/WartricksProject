
package com.wartricks.components;

import com.artemis.Component;

public class ScaleAnimation extends Component {
    public float min, max, speed;

    public boolean repeat, active;

    public ScaleAnimation(float speed) {
        this.speed = speed;
        min = 0f;
        max = 100f;
        repeat = false;
        active = true;
    }
}
