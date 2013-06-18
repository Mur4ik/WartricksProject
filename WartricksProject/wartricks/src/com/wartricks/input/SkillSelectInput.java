
package com.wartricks.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.SkillSet;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.logic.VersusGame;

public class SkillSelectInput implements InputProcessor {
    private OrthographicCamera camera;

    private VersusGame game;

    public SkillSelectInput(OrthographicCamera camera, VersusGame game) {
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
        if (game.state.getCurrentState() == GameState.CHOOSING_SKILL) {
            final int randSkill = (int)(Math.random() * 100);
            // TODO always same skill
            final int skillId = game.world.getEntity(game.state.getSelectedCreature())
                    .getComponent(SkillSet.class).skillSet.first();
            return game.selectSkill(skillId);
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
