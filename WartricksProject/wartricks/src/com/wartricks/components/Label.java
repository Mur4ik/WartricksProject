
package com.wartricks.components;

import com.artemis.Component;

public class Label extends Component {
    public String text;

    public Layer layer;

    public enum Layer {
        DEFAULT, BACKGROUND, ACTORS_1, ACTORS_2, ACTORS_3, PARTICLES;
        public int getLayerId() {
            return this.ordinal();
        }
    }

    public Label(String text, Layer layer) {
        this.text = text;
        this.layer = layer;
    }

    public Label(String name) {
        this(name, Layer.DEFAULT);
    }

    public Label() {
        this("default", Layer.DEFAULT);
    }
}
