
package com.wartricks.lifecycle;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AbstractScreen implements Screen {
    protected final BoardGame game;

    protected final World world;

    protected final OrthographicCamera camera;

    protected final Stage stage;

    public AbstractScreen(BoardGame game, World world) {
        this.game = game;
        this.world = world;
        camera = new OrthographicCamera();
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        world.setDelta(delta);
        world.process();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
        BoardGame.WINDOW_WIDTH = width;
        BoardGame.WINDOW_HEIGHT = height;
        camera.setToOrtho(false, width, height);
        stage.setViewport(width, height, false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
