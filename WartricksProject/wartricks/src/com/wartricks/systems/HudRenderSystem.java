
package com.wartricks.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wartricks.components.Label;
import com.wartricks.components.Position;

public class HudRenderSystem extends EntitySystem {
    @Mapper
    ComponentMapper<Position> pm;

    @Mapper
    ComponentMapper<Label> lm;

    private List<Entity> sortedEntities;

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private BitmapFont bitmapFont;

    @SuppressWarnings("unchecked")
    public HudRenderSystem(OrthographicCamera camera) {
        super(Aspect.getAspectForAll(Position.class, Label.class));
        this.camera = camera;
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for (final Entity e : sortedEntities) {
            this.process(e);
        }
    }

    @Override
    protected void initialize() {
        sortedEntities = new ArrayList<Entity>();
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont(Gdx.files.internal("data/verdana39.fnt"), false);
        bitmapFont.setColor(Color.RED);
    }

    private void process(Entity e) {
        final Position position = pm.get(e);
        final Label label = lm.get(e);
        bitmapFont.draw(batch, label.text, position.x, position.y);
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected void inserted(Entity e) {
        sortedEntities.add(e);
        Collections.sort(sortedEntities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                final Label l1 = lm.get(e1);
                final Label l2 = lm.get(e2);
                return l1.layer.compareTo(l2.layer);
            }
        });
    }

    @Override
    protected void removed(Entity e) {
        sortedEntities.remove(e);
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }
}
