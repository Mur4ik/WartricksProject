
package com.wartricks.input;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.Owner;
import com.wartricks.custom.Pair;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.Constants;
import com.wartricks.utils.EntityFactory;

public class CreatureSelectInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    @Mapper
    ComponentMapper<Owner> om;

    public CreatureSelectInput(OrthographicCamera camera, VersusGame game) {
        super();
        this.camera = camera;
        this.game = game;
        om = game.world.getMapper(Owner.class);
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
        if ((game.state.getCurrentState() == GameState.CHOOSING_CHARACTER) && (button == 0)) {
            final Pair coords = game.map.tools.window2world(Gdx.input.getX(), Gdx.input.getY());
            if ((coords.x >= 0) && (coords.x <= (Constants.HEX_MAP_WIDTH - 1)) && (coords.y >= 0)
                    && (coords.y <= (Constants.HEX_MAP_HEIGHT - 1))) {
                final int entityId = game.map.getEntityAt(coords.x, coords.y);
                if ((entityId > -1) && !game.state.getSelectedIds().contains(entityId, false)) {
                    final Entity e = game.world.getEntity(entityId);
                    final Owner owner = om.get(e);
                    if (owner.owner == game.state.getActivePlayer()) {
                        game.state.setSelectedCreature(entityId);
                        EntityFactory.createClick(game.world, coords.x, coords.y, 0.4f, 4f,
                                0.15f).addToWorld();
                        game.state.setCurrentState(GameState.CHOOSING_SKILL);
                    }
                    return true;
                }
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
