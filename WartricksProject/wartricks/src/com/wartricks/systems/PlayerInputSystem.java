
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.wartricks.components.Player;
import com.wartricks.components.Position;
import com.wartricks.utils.MapTools;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
    @Mapper
    ComponentMapper<Position> pm;

    private OrthographicCamera camera;

    private Vector3 mouseVector;

    @SuppressWarnings("unchecked")
    public PlayerInputSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Player.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void process(Entity e) {
        // TODO never gets called
        mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseVector);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseVector);
        final int x = (int)((mouseVector.x - 6f) / MapTools.col_multiple);
        final int y = (int)((mouseVector.y - (((float)MapTools.row_multiple * (x % 2)) / 2)) / MapTools.row_multiple);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // final Vector3 delta = new Vector3(-camera.zoom * Gdx.input.getDeltaX(), camera.zoom
        // * Gdx.input.getDeltaY(), 0);
        // camera.translate(delta);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (((camera.zoom > 0.2f) || (amount == 1)) && ((camera.zoom < 8) || (amount == -1))) {
            camera.zoom += amount * 0.1;
        }
        return false;
    }
}
