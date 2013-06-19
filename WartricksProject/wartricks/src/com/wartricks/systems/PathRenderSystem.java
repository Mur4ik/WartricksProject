
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
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.ScriptExecutable;
import com.wartricks.custom.FloatPair;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.PlatformUtils;

public class PathRenderSystem extends EntitySystem {
    @Mapper
    ComponentMapper<ActionSequence> mm;

    @Mapper
    private ComponentMapper<ScriptExecutable> sem;

    private OrthographicCamera camera;

    private SpriteBatch spriteBatch;

    // private Texture feet;
    private BitmapFont font;

    private VersusGame game;

    @SuppressWarnings("unchecked")
    public PathRenderSystem(OrthographicCamera camera, SpriteBatch batch, VersusGame game) {
        super(Aspect.getAspectForAll(ActionSequence.class));
        this.camera = camera;
        spriteBatch = batch;
        this.game = game;
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
        final ActionSequence moves = mm.get(e);
        font.setColor(moves.pathColor);
        if (!moves.onCastActions.isEmpty()) {
            Action move;
            for (int i = 0; i < moves.onCastActions.size(); i++) {
                move = moves.onCastActions.get(i);
                if (!move.origin.equals(move.target)) {
                    final ScriptExecutable script = sem.getSafe(game.world.getEntity(move.skillId));
                    final FloatPair coordsOrigin = game.map.tools.world2window(move.target.x,
                            move.target.y);
                    font.draw(spriteBatch, script.name, coordsOrigin.x - (game.map.colSize / 2),
                            coordsOrigin.y - 10);
                }
            }
        }
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }
}
