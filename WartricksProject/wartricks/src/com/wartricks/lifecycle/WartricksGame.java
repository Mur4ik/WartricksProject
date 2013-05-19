
package com.wartricks.lifecycle;

import com.badlogic.gdx.Game;

public class WartricksGame extends Game {
    @Override
    public void create() {
        // ImagePacker.run();
        this.setScreen(new BoardScene(this));
    }
}
