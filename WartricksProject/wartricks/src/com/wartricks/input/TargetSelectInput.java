
package com.wartricks.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.custom.Pair;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;

public class TargetSelectInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    public TargetSelectInput(OrthographicCamera camera, VersusGame game) {
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
        if ((game.state.getCurrentState() == GameState.CHOOSING_TARGET) && (button == 0)) {
            final Pair coords = game.map.tools.window2world(Gdx.input.getX(), Gdx.input.getY());
            if (game.map.highlighted.contains(coords, false)) {
                game.state.setSelectedHex(coords);
                game.state.setCurrentState(GameState.CHOOSING_CONFIRM);
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
