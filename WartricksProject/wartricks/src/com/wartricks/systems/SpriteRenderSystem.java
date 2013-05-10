
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wartricks.components.Position;
import com.wartricks.components.Sprite;

public class SpriteRenderSystem extends EntitySystem {
    @Mapper
    ComponentMapper<Position> pm;

    @Mapper
    ComponentMapper<Sprite> sm;

    private OrthographicCamera camera;

    private SpriteBatch batch;

    @SuppressWarnings("unchecked")
    public SpriteRenderSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Position.class, Sprite.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        batch = new SpriteBatch();
    }

    @Override
    protected boolean checkProcessing() {
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
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    protected void process(Entity e) {
        if (pm.has(e)) {
            final Position position = pm.getSafe(e);
            final Sprite sprite = sm.get(e);
            batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);
            final float posx = position.x;
            final float posy = position.y;
            batch.draw(sprite.sprite, posx, posy);
        }
    }

    @Override
    protected void end() {
        batch.end();
    }
}
