
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wartricks.boards.GameMap;
import com.wartricks.components.Move;
import com.wartricks.components.Path;
import com.wartricks.custom.FloatPair;
import com.wartricks.utils.PlatformUtils;

public class PathRenderSystem extends EntitySystem {
    @Mapper
    ComponentMapper<Path> mm;

    private OrthographicCamera camera;

    private SpriteBatch spriteBatch;

    // private Texture feet;
    private BitmapFont font;

    private GameMap gameMap;

    @SuppressWarnings("unchecked")
    public PathRenderSystem(OrthographicCamera camera, SpriteBatch batch, GameMap map) {
        super(Aspect.getAspectForAll(Path.class));
        this.camera = camera;
        spriteBatch = batch;
        gameMap = map;
    }

    @Override
    protected void initialize() {
        // feet = new
        // Texture(Gdx.files.internal(PlatformUtils.getPath("textures/effects/feet.png")));
        final Texture fontTexture = new Texture(Gdx.files.internal(PlatformUtils
                .getPath("resources/fonts/normal_0.png")));
        fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
        final TextureRegion fontRegion = new TextureRegion(fontTexture);
        font = new BitmapFont(Gdx.files.internal(PlatformUtils
                .getPath("resources/fonts/normal.fnt")), fontRegion, false);
        font.setScale(0.6f);
        font.setUseIntegerPositions(false);
    }

    @Override
    protected boolean checkProcessing() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            this.process(entities.get(i));
        }
    }

    @Override
    protected void begin() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    private void process(Entity e) {
        final Path moves = mm.get(e);
        font.setColor(moves.pathColor);
        if (!moves.path.isEmpty()) {
            Move move;
            for (int i = 0; i < moves.path.size(); i++) {
                move = moves.path.get(i);
                if (!move.origin.equals(move.destination)) {
                    final FloatPair coordsOrigin = gameMap.mapTools.world2window(move.origin.x,
                            move.origin.y);
                    font.draw(spriteBatch, String.valueOf(i), coordsOrigin.x - 10,
                            coordsOrigin.y - 10);
                    // spriteBatch.draw(feet, coordsOrigin.x - (feet.getWidth() / 2), coordsOrigin.y
                    // - (feet.getHeight() / 2));
                }
            }
        }
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }
}
