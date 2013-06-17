
package com.wartricks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.custom.Pair;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.Constants;
import com.wartricks.utils.EntityFactory;

public class CreatureSelectInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    public CreatureSelectInput(OrthographicCamera camera, VersusGame game) {
        super();
        this.camera = camera;
        this.game = game;
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
                EntityFactory.createClick(game.world, coords.x, coords.y, 0.4f, 4f, 0.15f)
                        .addToWorld();
                final int creatureId = game.map.getEntityAt(coords.x, coords.y);
                return game.selectCreature(creatureId);
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
