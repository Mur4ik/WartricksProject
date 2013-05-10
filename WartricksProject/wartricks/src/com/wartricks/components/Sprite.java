
package com.wartricks.components;

import com.artemis.Component;

public class Sprite extends Component {
    public enum Layer {
        DEFAULT, BACKGROUND, ACTORS_1, ACTORS_2, ACTORS_3, PARTICLES;
        public int getLayerId() {
            return this.ordinal();
        }
    }

    public Sprite(String name, Layer layer) {
        this.name = name;
        this.layer = layer;
    }

    public Sprite(String name) {
        this(name, Layer.DEFAULT);
    }

    public Sprite() {
        this("default", Layer.DEFAULT);
    }

    public String name;

    public float r = 1;

    public float g = 1;

    public float b = 1;

    public float a = 1;

    public float scaleX = 1;

    public float scaleY = 1;

    public float rotation;

    public Layer layer = Layer.DEFAULT;
}
