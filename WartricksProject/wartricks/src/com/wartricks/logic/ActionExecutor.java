
package com.wartricks.logic;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.MapPosition;
import com.wartricks.components.OnBeginTurn;
import com.wartricks.components.OnCast;
import com.wartricks.components.OnEndTurn;
import com.wartricks.custom.Pair;

public class ActionExecutor {
    VersusGame game;

    @Mapper
    ComponentMapper<OnCast> ocm;

    @Mapper
    ComponentMapper<OnBeginTurn> bm;

    @Mapper
    ComponentMapper<OnEndTurn> em;

    public ActionExecutor(VersusGame game) {
        super();
        this.game = game;
        ocm = game.gameWorld.getMapper(OnCast.class);
        bm = game.gameWorld.getMapper(OnBeginTurn.class);
        em = game.gameWorld.getMapper(OnEndTurn.class);
    }

    public boolean executeCast(Action action) {
        final OnCast executable = ocm.getSafe(game.gameWorld.getEntity(action.skillId));
        if (null != executable) {
            this.execute(executable.path, executable.method, game, action.creatureId,
                    action.origin, action.target);
            return true;
        }
        return false;
    }

    public boolean executeBeginTurn(Action action) {
        final OnBeginTurn executable = bm.get(game.gameWorld.getEntity(action.skillId));
        if (null != executable) {
            this.execute(executable.path, executable.method, game, action.creatureId,
                    action.origin, action.target);
            return true;
        }
        return false;
    }

    public boolean executeEndTurn(Action action) {
        final OnEndTurn executable = em.get(game.gameWorld.getEntity(action.skillId));
        if (null != executable) {
            this.execute(executable.path, executable.method, game, action.creatureId,
                    action.origin, action.target);
            return true;
        }
        return false;
    }

    private boolean execute(String script, String method, VersusGame game, int caster, Pair origin,
            Pair target) {
        if (script == "move") {
            game.gameMap.moveEntity(caster, target);
            final ComponentMapper<MapPosition> mm = game.gameWorld.getMapper(MapPosition.class);
            final Entity creature = game.gameWorld.getEntity(caster);
            final MapPosition mapPosition = mm.get(creature);
            mapPosition.position = target;
            creature.changedInWorld();
        }
        if (script == "jump") {
            final Entity creature = game.gameWorld.getEntity(caster);
            final ActionSequence sequence = game.gameWorld.getMapper(ActionSequence.class).get(
                    creature);
            sequence.onBeginTurnActions.add(new Action(caster, 0, origin, new Pair(origin.x,
                    origin.y - 1)));
            creature.changedInWorld();
        }
        return true;
        // TODO candidates
        // final LoadScript script = new LoadScript(path);
        // script.runUnboundScriptFunction(method, gameMap, gameWorld, caster);
    }
}
