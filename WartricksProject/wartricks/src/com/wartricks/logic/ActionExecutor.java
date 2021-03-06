
package com.wartricks.logic;

import bsh.EvalError;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.wartricks.components.ScriptExecutable;
import com.wartricks.custom.Pair;
import com.wartricks.custom.WartricksInterpreter;

public class ActionExecutor {
    private VersusGame game;

    @Mapper
    ComponentMapper<ScriptExecutable> ocm;

    public ActionExecutor(VersusGame game) {
        super();
        this.game = game;
        ocm = game.world.getMapper(ScriptExecutable.class);
    }

    public boolean execute(int casterId, int skillId, Pair origin, Pair target, String method) {
        final ScriptExecutable executable = ocm.getSafe(game.world.getEntity(skillId));
        if (null != executable) {
            try {
                final WartricksInterpreter interp = executable.interpreter;
                interp.set("game", game.api);
                interp.set("caster", casterId);
                interp.set("origin", origin);
                interp.set("target", target);
                interp.set("skill", skillId);
                interp.eval(method + "()");
            } catch (final EvalError e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    // private boolean execute(String script, String method, VersusGame game, int caster, Pair
    // origin,
    // Pair target) {
    // if (script == "move") {
    // // if (!game.map.cellOccupied(target.x, target.y)) {
    // // game.map.moveEntity(caster, target);
    // // final ComponentMapper<MapPosition> mm = game.world.getMapper(MapPosition.class);
    // // final Entity creature = game.world.getEntity(caster);
    // // final MapPosition mapPosition = mm.get(creature);
    // // mapPosition.position = target;
    // // creature.changedInWorld();
    // // } else {
    // // final ComponentMapper<ActionSequence> asq = game.world
    // // .getMapper(ActionSequence.class);
    // // final ActionSequence seq = asq.get(game.world.getEntity(caster));
    // // seq.onCastActions.clear();
    // // }
    // try {
    // final LoadScript scriptHandle = new LoadScript(PlatformUtils.getPath("skills/"
    // + script + ".lua"));
    // scriptHandle.runUnboundScriptFunction(method, game, caster, origin, target);
    // } catch (final Exception e) {
    // System.out.println("LUA peto");
    // }
    // }
    // if (script == "jump") {
    // final Entity creature = game.world.getEntity(caster);
    // final ActionSequence sequence = game.world.getMapper(ActionSequence.class)
    // .get(creature);
    // sequence.onBeginTurnActions.add(new Action(caster, 0, origin, new Pair(origin.x,
    // origin.y - 1)));
    // creature.changedInWorld();
    // }
    // if (script == "attack") {
    // final Entity creature = game.world.getEntity(caster);
    // final ActionSequence sequence = game.world.getMapper(ActionSequence.class)
    // .get(creature);
    // sequence.onEndTurnActions.add(new Action(caster, 0, origin, new Pair(origin.x,
    // origin.y + 1)));
    // creature.changedInWorld();
    // }
    // return true;
    // // TODO candidates
    // // final LoadScript script = new LoadScript(path);
    // // script.runUnboundScriptFunction(method, gameMap, gameWorld, caster);
    // }
}
