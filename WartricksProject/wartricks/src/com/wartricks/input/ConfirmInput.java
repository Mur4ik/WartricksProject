
package com.wartricks.input;

import com.artemis.Entity;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.Constants.Players;

public class ConfirmInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    public ConfirmInput(OrthographicCamera camera, VersusGame game) {
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
        if ((game.gameState.getCurrentState() == GameState.CHOOSING_CONFIRM) && (button == 0)) {
            if (true) {
                final Entity e = game.gameWorld.getEntity(game.gameState.getSelectedCreature());
                game.execute(e);
                game.gameMap.highlighted.clear();
                game.gameState.setSelectedCreature(-1);
                game.gameState.setSelectedSkill(-1);
                game.gameState.setSelectedHex(null);
                if (game.gameState.getActivePlayer() == Players.ONE) {
                    game.gameState.setActivePlayer(Players.TWO);
                } else {
                    game.gameState.setActivePlayer(Players.ONE);
                }
            }
            return true;
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
