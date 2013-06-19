
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
import com.wartricks.components.Cooldown;
import com.wartricks.components.Cost;
import com.wartricks.components.Initiative;
import com.wartricks.components.Range;
import com.wartricks.components.ScriptExecutable;
import com.wartricks.components.SkillSet;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.PlatformUtils;

public class SkillRenderSystem extends EntityProcessingSystem {
    private SpriteBatch spriteBatch;

    private BitmapFont font;

    private OrthographicCamera camera;

    private VersusGame game;

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

    @Mapper
    ComponentMapper<ScriptExecutable> ocm;

    @SuppressWarnings("unchecked")
    public SkillRenderSystem(OrthographicCamera camera, SpriteBatch batch, VersusGame game) {
        super(Aspect.getAspectForAll(SkillSet.class));
        spriteBatch = batch;
        this.camera = camera;
        this.game = game;
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
        if (game.state.getSelectedCreature() == e.getId()) {
            spriteBatch.setColor(1, 1, 1, 1);
            final SkillSet skills = sm.get(e);
            final int margin = 15;
            for (int i = 0; i < skills.skillSet.size; i++) {
                final int skillId = skills.skillSet.get(i);
                final Entity skill = world.getEntity(skillId);
                if (null != skill) {
                    final Range range = rm.getSafe(skill);
                    final Cost cost = cm.getSafe(skill);
                    final Cooldown cooldown = cdm.getSafe(skill);
                    final Initiative initiative = im.getSafe(skill);
                    final ScriptExecutable onCast = ocm.getSafe(skill);
                    String skillDescription = "%s (%d): Cost %d Range %d-%d Cooldown %d/%d Initiative %d";
                    skillDescription = String
                            .format(skillDescription, onCast.name, skillId, cost.getCostBase(),
                                    range.getMinRangeAfterModifiers(),
                                    range.getMaxRangeAfterModifiers(),
                                    cooldown.getCurrentCooldown(),
                                    cooldown.getMaxCooldownAfterModifiers(),
                                    initiative.getInitiativeBase());
                    font.draw(spriteBatch, skillDescription, 20, 100 + (margin * i));
                }
            }
        }
    }
}
