
package com.wartricks.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.wartricks.components.MapPosition;
import com.wartricks.components.Owner;
import com.wartricks.components.Sprite;
import com.wartricks.custom.FloatPair;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.Constants.Players;
import com.wartricks.utils.PlatformUtils;

public class SpriteRenderSystem extends EntitySystem {
    @Mapper
    ComponentMapper<MapPosition> pm;

    @Mapper
    ComponentMapper<Sprite> sm;

    @Mapper
    ComponentMapper<Owner> om;

    private OrthographicCamera camera;

    private SpriteBatch spriteBatch;

    private TextureAtlas atlas;

    private HashMap<String, AtlasRegion> regions;

    private Bag<AtlasRegion> regionsByEntity;

    private List<Entity> sortedEntities;

    private VersusGame game;

    @SuppressWarnings("unchecked")
    public SpriteRenderSystem(OrthographicCamera camera, SpriteBatch batch, VersusGame game) {
        super(Aspect.getAspectForAll(MapPosition.class, Sprite.class, Owner.class));
        this.camera = camera;
        spriteBatch = batch;
        this.game = game;
    }

    @Override
    protected void initialize() {
        atlas = new TextureAtlas(Gdx.files.internal(PlatformUtils
                .getPath("resources/textures/characters.atlas")), Gdx.files.internal(PlatformUtils
                .getPath("resources/textures/")));
        regions = new HashMap<String, AtlasRegion>();
        for (final AtlasRegion r : atlas.getRegions()) {
            regions.put(r.name, r);
        }
        regionsByEntity = new Bag<AtlasRegion>();
        sortedEntities = new ArrayList<Entity>();
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for (final Entity e : sortedEntities) {
            this.process(e);
        }
    }

    @Override
    protected void begin() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    protected void process(Entity e) {
        if (pm.has(e)) {
            final MapPosition mapPosition = pm.getSafe(e);
            final FloatPair position = game.map.tools.world2window(mapPosition.getPosition().x,
                    mapPosition.getPosition().y);
            final Sprite sprite = sm.get(e);
            final Owner owner = om.get(e);
            final AtlasRegion spriteRegion = regionsByEntity.get(e.getId());
            final float alpha = (game.state.getSelectedIds().contains(e.getId(), true) || (game.state
                    .getActivePlayer() != owner.getOwner())) ? 0.5f : 1.0f;
            if (owner.getOwner() == Players.TWO) {
                spriteBatch.setColor(sprite.r - 0.2f, sprite.g, sprite.b - 0.2f, alpha);
            } else {
                spriteBatch.setColor(sprite.r - 0.2f, sprite.g - 0.2f, sprite.b, alpha);
            }
            final float posX = position.x - ((spriteRegion.getRegionWidth() / 2) * sprite.scaleX);
            final float posY = position.y - ((spriteRegion.getRegionHeight() / 2) * sprite.scaleX);
            spriteBatch.draw(spriteRegion, posX, posY, 0, 0, spriteRegion.getRegionWidth(),
                    spriteRegion.getRegionHeight(), sprite.scaleX, sprite.scaleY, sprite.rotation);
        }
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }

    @Override
    protected void inserted(Entity e) {
        final Sprite sprite = sm.get(e);
        regionsByEntity.set(e.getId(), regions.get(sprite.name));
        sortedEntities.add(e);
        Collections.sort(sortedEntities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                final Sprite s1 = sm.get(e1);
                final Sprite s2 = sm.get(e2);
                return s1.layer.compareTo(s2.layer);
            }
        });
    }

    @Override
    protected void removed(Entity e) {
        regionsByEntity.set(e.getId(), null);
        sortedEntities.remove(e);
    }
}
