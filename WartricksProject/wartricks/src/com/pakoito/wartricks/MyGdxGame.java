
package com.pakoito.wartricks;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame implements ApplicationListener {
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Texture texture;

    private Sprite sprite;

    private BitmapFont font;

    private LoadScript script;

    private String text = "ERROR:Not Loading!";

    @Override
    public void create() {
        camera = new OrthographicCamera(1000, 1500);
        camera.position.set(0, 0, 0);
        batch = new SpriteBatch();
        font = new BitmapFont();
        // Load a script with my LoadScript Class
        script = new LoadScript("helloworld.lua");
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // This is loading a Global with my class
        text = script.getGlobalString("int");
        // Run a Lua Function with my class
        script.runScriptFunction("render", Gdx.graphics.getGL10());
        // Update doesnt work "properly" you can edit the script and the app will change
        // But it will crash every once in a while :/
        script.runScriptFunction("wave", null);
        script.update();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        batch.begin();
        // Draw the Global
        // font.draw(batch, text, 100, 100);
        // Run a Lua Function with two objects with my class
        // You can edit LoadScript.java and add more objects
        script.runScriptFunction("fontBatch", font, batch);
        batch.end();
    }

    @Override
    public void resize(final int width, final int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
