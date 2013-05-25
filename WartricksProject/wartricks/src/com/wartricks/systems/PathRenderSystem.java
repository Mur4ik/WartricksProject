
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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wartricks.components.Movement;
import com.wartricks.components.Path;
import com.wartricks.custom.FloatPair;
import com.wartricks.utils.MapTools;
import com.wartricks.utils.PlatformUtils;

public class PathRenderSystem extends EntitySystem {
    @Mapper
    ComponentMapper<Path> mm;

    private OrthographicCamera camera;

    private SpriteBatch spriteBatch;

    private Texture feet;

    @SuppressWarnings("unchecked")
    public PathRenderSystem(OrthographicCamera camera, SpriteBatch batch) {
        super(Aspect.getAspectForAll(Path.class));
        this.camera = camera;
        spriteBatch = batch;
    }

    @Override
    protected void initialize() {
        feet = new Texture(Gdx.files.internal(PlatformUtils.getPath("textures/effects/feet.png")));
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
        spriteBatch.begin();
    }

    private void process(Entity e) {
        final Path moves = mm.get(e);
        if (!moves.path.isEmpty()) {
            for (final Movement move : moves.path) {
                final FloatPair coordsOrigin = MapTools.world2window(move.originX, move.originY);
                final FloatPair coordsDestination = MapTools.world2window(move.destinationX,
                        move.destinationY);
                spriteBatch.draw(feet, coordsOrigin.x - (feet.getWidth() / 2), coordsOrigin.y
                        - (feet.getHeight() / 2));
                spriteBatch.draw(feet, coordsDestination.x - (feet.getWidth() / 2),
                        coordsDestination.y - (feet.getHeight() / 2));
            }
        }
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }
}
