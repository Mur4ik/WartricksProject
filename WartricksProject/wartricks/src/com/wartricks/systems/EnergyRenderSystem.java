
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wartricks.components.EnergyBar;
import com.wartricks.components.Owner;
import com.wartricks.utils.Constants.Players;
import com.wartricks.utils.PlatformUtils;

public class EnergyRenderSystem extends EntityProcessingSystem {
    private SpriteBatch spriteBatch;

    private BitmapFont font;

    private OrthographicCamera camera;

    @Mapper
    ComponentMapper<EnergyBar> em;

    @Mapper
    ComponentMapper<Owner> om;

    @SuppressWarnings("unchecked")
    public EnergyRenderSystem(OrthographicCamera camera, SpriteBatch batch) {
        super(Aspect.getAspectForAll(EnergyBar.class, Owner.class));
        spriteBatch = batch;
        this.camera = camera;
    }

    @Override
    protected void begin() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    @Override
    protected void end() {
        spriteBatch.end();
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
    protected void process(Entity e) {
        spriteBatch.setColor(1, 1, 1, 1);
        final EnergyBar energy = em.get(e);
        final Owner owner = om.get(e);
        if (null != energy) {
            spriteBatch.setColor(1, 1, 1, 1);
            int margin = 20;
            if (owner.owner.equals(Players.ONE)) {
                margin *= 3;
            }
            font.draw(spriteBatch, "Energy for player " + owner.owner.toString() + ": "
                    + energy.currentEnergy + "/" + (energy.maxEnergy + energy.modifierEnergy), 20,
                    margin);
        }
    }
}
