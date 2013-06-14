
package com.wartricks.systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wartricks.lifecycle.BoardGame;
import com.wartricks.utils.PlatformUtils;

public class HudRenderSystem extends VoidEntitySystem {
    private SpriteBatch spriteBatch;

    private BitmapFont font;

    private OrthographicCamera camera;

    public HudRenderSystem(OrthographicCamera camera, SpriteBatch batch) {
        spriteBatch = batch;
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        final Texture fontTexture = new Texture(Gdx.files.internal(PlatformUtils
                .getPath("resources/fonts/normal_0.png")));
        fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
        final TextureRegion fontRegion = new TextureRegion(fontTexture);
        font = new BitmapFont(Gdx.files.internal(PlatformUtils
                .getPath("resources/fonts/normal.fnt")), fontRegion, false);
        font.setColor(Color.DARK_GRAY);
        font.setUseIntegerPositions(false);
    }

    @Override
    protected void begin() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    @Override
    protected void processSystem() {
        spriteBatch.setColor(1, 1, 1, 1);
        final float screenHeight = BoardGame.WINDOW_HEIGHT;
        font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, screenHeight - 20);
        font.draw(spriteBatch, "Active entities: "
                + world.getEntityManager().getActiveEntityCount(), 20, screenHeight - 40);
        font.draw(spriteBatch, "Total created: " + world.getEntityManager().getTotalCreated(), 20,
                screenHeight - 60);
        font.draw(spriteBatch, "Total deleted: " + world.getEntityManager().getTotalDeleted(), 20,
                screenHeight - 80);
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }
}
