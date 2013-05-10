
package com.wartricks.lifecycle;

import com.badlogic.gdx.Game;

public class WartricksGame extends Game {
    @Override
    public void create() {
        this.setScreen(new BoardScene(this));
    }
}
