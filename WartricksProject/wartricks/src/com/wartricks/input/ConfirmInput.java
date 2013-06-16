
package com.wartricks.input;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.MapPosition;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;

public class ConfirmInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    private ComponentMapper<ActionSequence> asm;

    private ComponentMapper<MapPosition> mm;

    public ConfirmInput(OrthographicCamera camera, VersusGame game) {
        super();
        this.camera = camera;
        this.game = game;
        asm = game.gameWorld.getMapper(ActionSequence.class);
        mm = game.gameWorld.getMapper(MapPosition.class);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            game.gameState.setCurrentState(GameState.PLAYER_FINISHED);
        }
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
            final Entity creature = game.gameWorld.getEntity(game.gameState.getSelectedCreature());
            final MapPosition position = mm.get(creature);
            final ActionSequence sequence = asm.get(creature);
            sequence.onCastActions.add(new Action(game.gameState.getSelectedCreature(),
                    game.gameState.getSelectedSkill(), position.position, game.gameState
                            .getSelectedHex()));
            game.gameState.getSelectedIds().add(game.gameState.getSelectedCreature());
            creature.changedInWorld();
            game.gameState.setSelectedCreature(-1);
            game.gameState.setSelectedSkill(-1);
            game.gameState.setSelectedHex(null);
            game.gameState.setCurrentState(GameState.CHOOSING_CHARACTER);
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
