
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.wartricks.components.Action;
import com.wartricks.components.ActionSequence;
import com.wartricks.components.MapPosition;
import com.wartricks.components.OnCast;
import com.wartricks.logic.VersusGame;

public class OnExecuteTurnSystem extends EntityProcessingSystem {
    private VersusGame game;

    @Mapper
    ComponentMapper<OnCast> ocm;

    @Mapper
    ComponentMapper<ActionSequence> asm;

    @SuppressWarnings("unchecked")
    public OnExecuteTurnSystem(VersusGame game) {
        super(Aspect.getAspectForAll(ActionSequence.class));
        this.game = game;
    }

    @Override
    protected void process(Entity e) {
        final ActionSequence sequence = asm.get(e);
        if (sequence.actions.size() > 0) {
            final Action action = sequence.actions.remove(0);
            final OnCast executable = ocm.getSafe(game.gameWorld.getEntity(action.skillId));
            // TODO placeholder
            if (executable.path == "move") {
                game.gameMap.moveEntity(e.getId(), action.target);
                final ComponentMapper<MapPosition> mm = game.gameWorld.getMapper(MapPosition.class);
                final MapPosition mapPosition = mm.get(e);
                mapPosition.position = action.target;
                e.changedInWorld();
            }
        }
    }
}
