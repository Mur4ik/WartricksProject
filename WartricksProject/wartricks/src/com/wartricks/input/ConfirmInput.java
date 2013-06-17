
package com.wartricks.input;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.Cost;
import com.wartricks.components.EnergyBar;
import com.wartricks.components.MapPosition;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;

public class ConfirmInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    private ComponentMapper<ActionSequence> asm;

    private ComponentMapper<MapPosition> mm;

    private ComponentMapper<EnergyBar> ebm;

    private ComponentMapper<Cost> com;

    public ConfirmInput(OrthographicCamera camera, VersusGame game) {
        super();
        this.camera = camera;
        this.game = game;
        asm = game.world.getMapper(ActionSequence.class);
        mm = game.world.getMapper(MapPosition.class);
        ebm = game.world.getMapper(EnergyBar.class);
        com = game.world.getMapper(Cost.class);
    }

    @Override
    public boolean keyDown(int keycode) {
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
        if ((game.state.getCurrentState() == GameState.CHOOSING_CONFIRM) && (button == 0)) {
            return game.confirmAction();
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
