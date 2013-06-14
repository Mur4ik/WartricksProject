
package com.wartricks.lifecycle;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wartricks.systems.ColorAnimationSystem;
import com.wartricks.systems.ExpiringSystem;
import com.wartricks.systems.ScaleAnimationSystem;

public class BoardGame extends Game {
    public static int WINDOW_WIDTH;

    public static int WINDOW_HEIGHT;

    public World world;

    private SpriteBatch batch;

    public BoardGame(int width, int height) {
        super();
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
    }

    @Override
    public void create() {
        // ImagePacker.run();
        world = new World();
        world.setSystem(new ExpiringSystem());
        world.setSystem(new ColorAnimationSystem());
        world.setSystem(new ScaleAnimationSystem());
        world.setManager(new GroupManager());
        world.setManager(new TagManager());
        world.initialize();
        batch = new SpriteBatch();
        this.setScreen(new BoardScene(this, world, batch));
    }
}
