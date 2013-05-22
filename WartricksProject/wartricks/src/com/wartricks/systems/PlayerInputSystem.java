
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.wartricks.components.Player;
import com.wartricks.components.Position;
import com.wartricks.components.Velocity;
import com.wartricks.utils.EntityFactory;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
    @Mapper
    ComponentMapper<Velocity> vm;

    @Mapper
    ComponentMapper<Position> pm;

    private OrthographicCamera camera;

    private Vector3 mouseVector;

    private int ax, ay;

    private final int thruster = 400;

    private final float drag = 0.4f;

    private boolean shoot = false;

    private float lastFired = 0;

    private float fireRate = 0.3f;

    @SuppressWarnings("unchecked")
    public PlayerInputSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Velocity.class, Player.class, Position.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void process(Entity e) {
        mouseVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseVector);
        final Velocity vel = vm.get(e);
        final Position pos = pm.get(e);
        vel.vx += (ax - (drag * vel.vx)) * world.getDelta();
        vel.vy += (ay - (drag * vel.vy)) * world.getDelta();
        lastFired += world.getDelta();
        if (shoot && (lastFired > fireRate)) {
            lastFired = 0;
            EntityFactory.createBullet(world, pos.x - 10, pos.y + 40).addToWorld();
            EntityFactory.createBullet(world, pos.x + 40, pos.y + 40).addToWorld();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            ay = thruster;
        }
        if (keycode == Input.Keys.DOWN) {
            ay = -thruster;
        }
        if (keycode == Input.Keys.RIGHT) {
            ax = thruster;
        }
        if (keycode == Input.Keys.LEFT) {
            ax = -thruster;
        }
        if (keycode == Input.Keys.SPACE) {
            shoot = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
            ay = 0;
        }
        if (keycode == Input.Keys.DOWN) {
            ay = 0;
        }
        if (keycode == Input.Keys.RIGHT) {
            ax = 0;
        }
        if (keycode == Input.Keys.LEFT) {
            ax = 0;
        }
        if (keycode == Input.Keys.SPACE) {
            shoot = false;
        }
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
