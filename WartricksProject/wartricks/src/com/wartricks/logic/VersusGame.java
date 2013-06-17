
package com.wartricks.logic;

import java.util.Observable;
import java.util.Observer;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.input.ConfirmInput;
import com.wartricks.input.CreatureSelectInput;
import com.wartricks.input.GeneralInput;
import com.wartricks.input.SkillSelectInput;
import com.wartricks.input.TargetSelectInput;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.systems.OnBeginTurnSystem;
import com.wartricks.systems.OnEndTurnSystem;
import com.wartricks.systems.OnExecuteTurnSystem;
import com.wartricks.utils.Constants.Players;

public class VersusGame implements Observer {
    private OrthographicCamera camera;

    public GameMap map;

    public World world;

    public ActionExecutor executor;

    public StateMachine state;

    public InputMultiplexer inputSystem;

    private TargetSelectInput targetSelectInput;

    private CreatureSelectInput creatureSelectInput;

    private SkillSelectInput skillSelectInput;

    private ConfirmInput confirmSelectInput;

    private GeneralInput generalInput;

    private OnBeginTurnSystem onBeginTurnSystem;

    private OnEndTurnSystem onEndTurnSystem;

    private OnExecuteTurnSystem onExecuteTurnSystem;

    public VersusGame(GameMap gameMap, World gameWorld, OrthographicCamera camera) {
        super();
        map = gameMap;
        world = gameWorld;
        this.camera = camera;
        executor = new ActionExecutor(this);
        onExecuteTurnSystem = gameWorld.setSystem(new OnExecuteTurnSystem(this), true);
        onBeginTurnSystem = gameWorld.setSystem(new OnBeginTurnSystem(this), true);
        onEndTurnSystem = gameWorld.setSystem(new OnEndTurnSystem(this), true);
        // playerInputSystem = gameWorld.setSystem(new PlayerInputSystem(camera, gameMap), false);
        inputSystem = new InputMultiplexer();
        generalInput = new GeneralInput(camera, this);
        creatureSelectInput = new CreatureSelectInput(camera, this);
        skillSelectInput = new SkillSelectInput(camera, this);
        targetSelectInput = new TargetSelectInput(camera, this);
        confirmSelectInput = new ConfirmInput(camera, this);
        inputSystem.addProcessor(creatureSelectInput);
        inputSystem.addProcessor(skillSelectInput);
        inputSystem.addProcessor(targetSelectInput);
        inputSystem.addProcessor(confirmSelectInput);
        inputSystem.addProcessor(generalInput);
        Gdx.input.setInputProcessor(inputSystem);
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
            final GameState gameState = (GameState)currentState;
            switch (gameState) {
                case BEGIN_TURN:
                    onBeginTurnSystem.process();
                    state.setActivePlayer(Players.ONE);
                    state.setCurrentState(GameState.CHOOSING_CHARACTER);
                case CHOOSING_CHARACTER:
                    map.clearHighlights();
                    break;
                case CHOOSING_CONFIRM:
                    break;
                case CHOOSING_SKILL:
                    break;
                case CHOOSING_TARGET:
                    break;
                case PLAYER_FINISHED:
                    state.clearSelectedIds();
                    map.clearHighlights();
                    if (Players.ONE == state.getActivePlayer()) {
                        state.setActivePlayer(Players.TWO);
                        state.setCurrentState(GameState.CHOOSING_CHARACTER);
                    } else if (Players.TWO == state.getActivePlayer()) {
                        onExecuteTurnSystem.process();
                        state.setCurrentState(GameState.END_TURN);
                    }
                    break;
                case END_TURN:
                    onEndTurnSystem.process();
                    state.setCurrentState(GameState.BEGIN_TURN);
                    break;
            }
        }
    }
}
