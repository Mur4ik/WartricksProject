
package com.wartricks.logic;

import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.Cost;
import com.wartricks.components.EnergyBar;
import com.wartricks.components.MapPosition;
import com.wartricks.components.Owner;
import com.wartricks.components.Range;
import com.wartricks.custom.FloatPair;
import com.wartricks.custom.Pair;
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
import com.wartricks.utils.MapTools.Shapes;

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

    @Mapper
    private ComponentMapper<Range> rm;

    @Mapper
    private ComponentMapper<MapPosition> mm;

    @Mapper
    private ComponentMapper<EnergyBar> ebm;

    @Mapper
    private ComponentMapper<Cost> com;

    @Mapper
    ComponentMapper<Owner> om;

    public VersusGame(GameMap gameMap, World gameWorld, OrthographicCamera camera) {
        super();
        map = gameMap;
        world = gameWorld;
        this.camera = camera;
        executor = new ActionExecutor(this);
        rm = world.getMapper(Range.class);
        mm = world.getMapper(MapPosition.class);
        ebm = world.getMapper(EnergyBar.class);
        com = world.getMapper(Cost.class);
        om = world.getMapper(Owner.class);
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
                    state.clearSelection();
                    break;
                case CHOOSING_CONFIRM:
                    break;
                case CHOOSING_SKILL:
                    break;
                case CHOOSING_TARGET:
                    break;
                case PLAYER_FINISHED:
                    state.clearSelection();
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

    public boolean selectSkill(int skillId) {
        if (skillId > -1) {
            final Entity skill = world.getEntity(skillId);
            final Entity player = world.getManager(TagManager.class).getEntity(
                    state.getActivePlayer().toString());
            final Cost cost = com.get(skill);
            final EnergyBar bar = ebm.get(player);
            if ((bar.currentEnergy - cost.getCostAfterModifiers()) >= 0) {
                final MapPosition origin = mm.get(world.getEntity(state.getSelectedCreature()));
                final Range range = rm.get(skill);
                map.addHighlightedShape(Shapes.CIRCLE, range.minRange, range.maxRange,
                        origin.position, new FloatPair(1, 1));
                state.setSelectedSkill(skillId);
                state.setCurrentState(GameState.CHOOSING_TARGET);
                return true;
            } else {
                // TODO SKILL TOO EXPENSIVE!
            }
        }
        return false;
    }

    public boolean selectCreature(int entityId) {
        if ((entityId > -1) && !state.getSelectedIds().contains(entityId, false)) {
            final Entity e = world.getEntity(entityId);
            final Owner owner = om.get(e);
            if (owner.owner == state.getActivePlayer()) {
                state.setSelectedCreature(entityId);
                state.setCurrentState(GameState.CHOOSING_SKILL);
            }
            return true;
        }
        return false;
    }

    public boolean undoLatestAction(int creatureId) {
        if (creatureId > -1) {
            try {
                final Action removed = world.getEntity(state.getSelectedCreature()).getComponent(
                        ActionSequence.class).onCastActions.removeLast();
                final Entity player = world.getManager(TagManager.class).getEntity(
                        state.getActivePlayer().toString());
                final Entity skill = world.getEntity(removed.skillId);
                final ComponentMapper<EnergyBar> ebm = world.getMapper(EnergyBar.class);
                final ComponentMapper<Cost> com = world.getMapper(Cost.class);
                final Cost cost = com.get(skill);
                final EnergyBar bar = ebm.get(player);
                bar.currentEnergy += cost.getCostAfterModifiers();
                player.changedInWorld();
            } catch (final NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean selectHexagon(Pair coords) {
        if (map.highlighted.contains(coords, false)) {
            state.setSelectedHex(coords);
            state.setCurrentState(GameState.CHOOSING_CONFIRM);
            return true;
        }
        return false;
    }
}
