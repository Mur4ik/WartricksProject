
package com.wartricks.logic;

import java.util.Observable;
import java.util.Observer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.MapPosition;
import com.wartricks.input.ConfirmInput;
import com.wartricks.input.CreatureSelectInput;
import com.wartricks.input.SkillSelectInput;
import com.wartricks.input.TargetSelectInput;
import com.wartricks.logic.StateMachine.GameState;
import com.wartricks.systems.OnBeginTurnSystem;
import com.wartricks.systems.OnEndTurnSystem;
import com.wartricks.systems.PlayerInputSystem;
import com.wartricks.utils.Constants.Players;

public class VersusGame implements Observer {
    private OrthographicCamera camera;

    public GameMap gameMap;

    public World gameWorld;

    public StateMachine gameState;

    public InputMultiplexer inputSystem;

    private PlayerInputSystem playerInputSystem;

    private TargetSelectInput targetSelectInput;

    private CreatureSelectInput creatureSelectInput;

    private SkillSelectInput skillSelectInput;

    private ConfirmInput confirmSelectInput;

    private OnBeginTurnSystem onBeginTurnSystem;

    private OnEndTurnSystem onEndTurnSystem;

    public VersusGame(GameMap gameMap, World gameWorld, OrthographicCamera camera) {
        super();
        this.gameMap = gameMap;
        this.gameWorld = gameWorld;
        this.camera = camera;
        onBeginTurnSystem = gameWorld.setSystem(new OnBeginTurnSystem(this), false);
        onEndTurnSystem = gameWorld.setSystem(new OnEndTurnSystem(this), false);
        // playerInputSystem = gameWorld.setSystem(new PlayerInputSystem(camera, gameMap), false);
        inputSystem = new InputMultiplexer();
        creatureSelectInput = new CreatureSelectInput(camera, this);
        skillSelectInput = new SkillSelectInput(camera, this);
        targetSelectInput = new TargetSelectInput(camera, this);
        confirmSelectInput = new ConfirmInput(camera, this);
        inputSystem.addProcessor(creatureSelectInput);
        inputSystem.addProcessor(skillSelectInput);
        inputSystem.addProcessor(targetSelectInput);
        inputSystem.addProcessor(confirmSelectInput);
        Gdx.input.setInputProcessor(inputSystem);
        gameState = new StateMachine();
    }

    public boolean startLogic() {
        gameState.addObserver(this);
        gameState.setCurrentState(GameState.CHOOSING_CHARACTER);
        return true;
    }

    @Override
    public void update(Observable obs, Object currentState) {
        if (currentState instanceof GameState) {
            final GameState state = (GameState)currentState;
            switch (state) {
                case CHOOSING_CHARACTER:
                    onBeginTurnSystem.process();
                    break;
                case CHOOSING_CONFIRM:
                    break;
                case CHOOSING_SKILL:
                    break;
                case CHOOSING_TARGET:
                    break;
                case RESOLVING_ACTIONS:
                    this.execute();
                    gameMap.highlighted.clear();
                    gameState.setSelectedCreature(-1);
                    gameState.setSelectedSkill(-1);
                    gameState.setSelectedHex(null);
                    onEndTurnSystem.process();
                    if (Players.ONE == gameState.getActivePlayer()) {
                        gameState.setActivePlayer(Players.TWO);
                    } else {
                        gameState.setActivePlayer(Players.ONE);
                    }
                    break;
            }
        }
    }

    public void execute() {
        // TODO placeholder
        gameMap.moveEntity(gameState.getSelectedCreature(), gameState.getSelectedHex());
        final Entity e = gameWorld.getEntity(gameState.getSelectedCreature());
        final ComponentMapper<MapPosition> mm = gameWorld.getMapper(MapPosition.class);
        final MapPosition mapPosition = mm.get(e);
        mapPosition.position = gameState.getSelectedHex();
        e.changedInWorld();
    }
}
