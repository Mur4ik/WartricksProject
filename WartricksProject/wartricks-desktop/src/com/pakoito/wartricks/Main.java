
package com.pakoito.wartricks;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wartricks.lifecycle.BoardGame;

public class Main {
    public static void main(final String[] args) {
        final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "wartricks";
        cfg.useGL20 = true;
        cfg.width = 1080;
        cfg.height = 576;
        cfg.resizable = false;
        new LwjglApplication(new BoardGame(cfg.width, cfg.height = 576), cfg);
    }
}
