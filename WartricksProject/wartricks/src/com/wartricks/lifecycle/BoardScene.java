
package com.wartricks.lifecycle;

import com.artemis.World;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.systems.ExpiringSystem;
import com.wartricks.systems.MovementSystem;
import com.wartricks.systems.PlayerInputSystem;
import com.wartricks.systems.SpriteRenderSystem;
import com.wartricks.tools.EntityFactory;

public class BoardScene implements Screen {
    private OrthographicCamera camera;

    private Game game;

    private World world;

    private SpriteRenderSystem spriteRenderSystem;

    private FPSLogger fpsLogger;

    public BoardScene(final Game game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 900);
        this.game = game;
        fpsLogger = new FPSLogger();
        world = new World();
        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);
        world.setSystem(new PlayerInputSystem(camera));
        world.setSystem(new MovementSystem());
        world.setSystem(new ExpiringSystem());
        world.initialize();
        LoadScript script;
        final FileHandle[] files;
        String path = "characters/";
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            path = "./assets/" + path;
        }
        files = Gdx.files.internal(path).list();
        for (final FileHandle file : files) {
            script = new LoadScript(file.path());
            script.runScriptFunction("create", EntityFactory.class, world);
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(final int width, final int height) {
        Gdx.app.debug(this.toString(), "resize");
    }

    @Override
    public void pause() {
        Gdx.app.debug(this.toString(), "pause");
    }

    @Override
    public void resume() {
        Gdx.app.debug(this.toString(), "resume");
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        fpsLogger.log();
        camera.update();
        world.setDelta(delta);
        world.process();
        spriteRenderSystem.process();
    }

    @Override
    public void show() {
        Gdx.app.debug(this.toString(), "show");
    }

    @Override
    public void hide() {
        Gdx.app.debug(this.toString(), "hide");
    }
}
