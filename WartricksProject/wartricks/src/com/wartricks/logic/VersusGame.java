
package com.wartricks.logic;

import java.util.Observable;
import java.util.Observer;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.systems.OnBeginTurnSystem;
import com.wartricks.systems.OnEndTurnSystem;
import com.wartricks.systems.PlayerInputSystem;

public class VersusGame implements Observer {
    public InputMultiplexer inputSystem;

    private PlayerInputSystem playerInputSystem;

    private OnBeginTurnSystem onBeginTurnSystem;

    private OnEndTurnSystem onEndTurnSystem;

    private GameMap gameMap;

    private World gameWorld;

    private OrthographicCamera camera;

    public StateMachine state;

    public VersusGame(GameMap gameMap, World gameWorld, OrthographicCamera camera) {
        super();
        this.gameMap = gameMap;
        this.gameWorld = gameWorld;
        this.camera = camera;
        playerInputSystem = gameWorld.setSystem(new PlayerInputSystem(camera, gameMap), false);
        onBeginTurnSystem = gameWorld.setSystem(new OnBeginTurnSystem(gameMap, gameWorld), false);
        onEndTurnSystem = gameWorld.setSystem(new OnEndTurnSystem(gameMap, gameWorld), false);
        Gdx.input.setInputProcessor(inputSystem);
        inputSystem = new InputMultiplexer(playerInputSystem);
        state = new StateMachine();
    }

    public boolean startLogic() {
        state.addObserver(this);
        state.setCurrentState(GameState.CHOOSING_CHARACTER);
        return true;
    }

    @Override
    public void update(Observable obs, Object currentState) {
        if (currentState instanceof GameState) {
            final GameState state = (GameState)currentState;
            switch (state) {
                case CHOOSING_CHARACTER:
                    break;
                case CHOOSING_CONFIRM:
                    break;
                case CHOOSING_SKILL:
                    break;
                case CHOOSING_TARGET:
                    break;
                case RESOLVING_ACTIONS:
                    onBeginTurnSystem.process();
                    onEndTurnSystem.process();
                    break;
            }
        }
    }
}
