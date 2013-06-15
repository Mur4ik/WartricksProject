
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wartricks.components.Cooldown;
import com.wartricks.components.Cost;
import com.wartricks.components.Initiative;
import com.wartricks.components.Range;
import com.wartricks.components.SelectedCreature;
import com.wartricks.components.SkillSet;
import com.wartricks.utils.PlatformUtils;

public class SkillRenderSystem extends EntityProcessingSystem {
    private SpriteBatch spriteBatch;

    private BitmapFont font;

    private OrthographicCamera camera;

    @Mapper
    ComponentMapper<SkillSet> sm;

    @Mapper
    ComponentMapper<Range> rm;

    @Mapper
    ComponentMapper<Cost> cm;

    @Mapper
    ComponentMapper<Cooldown> cdm;

    @Mapper
    ComponentMapper<Initiative> im;

    @SuppressWarnings("unchecked")
    public SkillRenderSystem(OrthographicCamera camera, SpriteBatch batch) {
        super(Aspect.getAspectForAll(SelectedCreature.class, SkillSet.class));
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
    protected void end() {
        spriteBatch.end();
    }

    @Override
    protected void process(Entity e) {
        spriteBatch.setColor(1, 1, 1, 1);
        final SkillSet skills = sm.get(e);
        final int margin = 15;
        for (int i = 0; i < skills.skillSet.size; i++) {
            final String skillName = skills.skillSet.get(i);
            final Entity skill = world.getManager(TagManager.class).getEntity(skillName);
            if (null != skill) {
                final Range range = rm.getSafe(skill);
                final Cost cost = cm.getSafe(skill);
                final Cooldown cooldown = cdm.getSafe(skill);
                final Initiative initiative = im.getSafe(skill);
                String skillDescription = "%s: Cost %d Range %d-%d Cooldown %d Initiative %d";
                skillDescription = String.format(skillDescription, skillName, cost.baseCost,
                        range.minRange, range.maxRange, cooldown.currentCooldown,
                        initiative.baseInitiative);
                font.draw(spriteBatch, skillDescription, 20, 100 + (margin * i));
            }
        }
    }
}