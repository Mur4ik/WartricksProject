
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
        rm = game.gameWorld.getMapper(Range.class);
        mm = game.gameWorld.getMapper(MapPosition.class);
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
        if (game.gameState.getCurrentState() == GameState.CHOOSING_SKILL) {
            final int skillId = game.gameWorld.getManager(TagManager.class).getEntity("move")
                    .getId();
            if (skillId > -1) {
                final Entity e = game.gameWorld.getEntity(skillId);
                final MapPosition origin = mm.get(game.gameWorld.getEntity(game.gameState
                        .getSelectedCreature()));
                final Range range = rm.get(e);
                game.gameMap.addHighlightedShape(Shapes.CIRCLE, range.minRange, range.maxRange,
                        origin.position, new FloatPair(1, 1));
                game.gameState.setSelectedSkill(skillId);
                game.gameState.setCurrentState(GameState.CHOOSING_TARGET);
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
