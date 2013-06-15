
package com.wartricks.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.MapPosition;
import com.wartricks.components.OnCast;
import com.wartricks.custom.ActionComparator;
import com.wartricks.logic.VersusGame;
import com.wartricks.utils.Constants.Groups;
import com.wartricks.utils.Constants.Players;

public class OnExecuteTurnSystem extends VoidEntitySystem {
    private VersusGame game;

    @Mapper
    ComponentMapper<OnCast> ocm;

    @Mapper
    ComponentMapper<ActionSequence> asm;

    @SuppressWarnings("unchecked")
    public OnExecuteTurnSystem(VersusGame game) {
        super();
        this.game = game;
    }

    @Override
    protected void processSystem() {
        String group;
        if (game.gameState.getActivePlayer() == Players.ONE) {
            group = Groups.PLAYER_ONE_CREATURE;
        } else {
            group = Groups.PLAYER_TWO_CREATURE;
        }
        final ImmutableBag<Entity> characters = game.gameWorld.getManager(GroupManager.class)
                .getEntities(group);
        final Array<Action> turn = new Array<Action>();
        do {
            for (int i = 0; i < characters.size(); i++) {
                final ActionSequence sequence = asm.get(characters.get(i));
                if (sequence.actions.size() > 0) {
                    turn.add(sequence.actions.remove(0));
                }
            }
            turn.sort(new ActionComparator(game.gameWorld));
            for (final Action action : turn) {
                final OnCast executable = ocm.getSafe(game.gameWorld.getEntity(action.skillId));
                // TODO placeholder
                if (executable.path == "move") {
                    game.gameMap.moveEntity(action.creatureId, action.target);
                    final ComponentMapper<MapPosition> mm = game.gameWorld
                            .getMapper(MapPosition.class);
                    final Entity caster = game.gameWorld.getEntity(action.creatureId);
                    final MapPosition mapPosition = mm.get(caster);
                    mapPosition.position = action.target;
                    caster.changedInWorld();
                }
                //
            }
            turn.clear();
        } while (turn.size > 0);
    }
}
