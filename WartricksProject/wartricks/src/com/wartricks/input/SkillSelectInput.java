
package com.wartricks.input;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.MapPosition;
import com.wartricks.components.Range;
import com.wartricks.custom.FloatPair;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.MapTools.Shapes;

public class SkillSelectInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    @Mapper
    ComponentMapper<Range> rm;

    @Mapper
    ComponentMapper<MapPosition> mm;

    public SkillSelectInput(OrthographicCamera camera, VersusGame game) {
        this.camera = camera;
        this.game = game;
        rm = game.world.getMapper(Range.class);
        mm = game.world.getMapper(MapPosition.class);
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (game.state.getCurrentState() == GameState.CHOOSING_SKILL) {
            final int randSkill = (int)(Math.random() * 100);
            int skillId = -1;
            if (randSkill < 30) {
                skillId = game.world.getManager(TagManager.class).getEntity("jump").getId();
            } else if (randSkill > 70) {
                skillId = game.world.getManager(TagManager.class).getEntity("attack").getId();
            } else {
                skillId = game.world.getManager(TagManager.class).getEntity("move").getId();
            }
            if (skillId > -1) {
                final Entity e = game.world.getEntity(skillId);
                final MapPosition origin = mm.get(game.world.getEntity(game.state
                        .getSelectedCreature()));
                final Range range = rm.get(e);
                game.map.addHighlightedShape(Shapes.CIRCLE, range.minRange, range.maxRange,
                        origin.position, new FloatPair(1, 1));
                game.state.setSelectedSkill(skillId);
                game.state.setCurrentState(GameState.CHOOSING_TARGET);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
