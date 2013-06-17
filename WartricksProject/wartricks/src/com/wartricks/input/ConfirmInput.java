
package com.wartricks.input;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.MapPosition;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.Constants.Groups;
import com.wartricks.utils.Constants.Players;

public class ConfirmInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    private ComponentMapper<ActionSequence> asm;

    private ComponentMapper<MapPosition> mm;

    public ConfirmInput(OrthographicCamera camera, VersusGame game) {
        super();
        this.camera = camera;
        this.game = game;
        asm = game.world.getMapper(ActionSequence.class);
        mm = game.world.getMapper(MapPosition.class);
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
            final Entity creature = game.world.getEntity(game.state.getSelectedCreature());
            final MapPosition position = mm.get(creature);
            final ActionSequence sequence = asm.get(creature);
            sequence.onCastActions.add(new Action(game.state.getSelectedCreature(), game.state
                    .getSelectedSkill(), position.position, game.state.getSelectedHex()));
            game.state.getSelectedIds().add(game.state.getSelectedCreature());
            creature.changedInWorld();
            game.state.setSelectedCreature(-1);
            game.state.setSelectedSkill(-1);
            game.state.setSelectedHex(null);
            String group;
            if (game.state.getActivePlayer() == Players.ONE) {
                group = Groups.PLAYER_ONE_CREATURE;
            } else {
                group = Groups.PLAYER_TWO_CREATURE;
            }
            if (game.state.getSelectedIds().size >= game.world.getManager(GroupManager.class)
                    .getEntities(group).size()) {
                game.state.setCurrentState(GameState.PLAYER_FINISHED);
            } else {
                game.state.setCurrentState(GameState.CHOOSING_CHARACTER);
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
