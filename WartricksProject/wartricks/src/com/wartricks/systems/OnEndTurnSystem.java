
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.wartricks.components.OnEndTurn;
import com.wartricks.logic.VersusGame;

public class OnEndTurnSystem extends EntityProcessingSystem {
    private VersusGame game;

    @Mapper
    ComponentMapper<OnEndTurn> tm;

    @SuppressWarnings("unchecked")
    public OnEndTurnSystem(VersusGame game) {
        super(Aspect.getAspectForAll(OnEndTurn.class));
        this.game = game;
    }

    @Override
    protected void process(Entity e) {
        // final OnEndTurn executable = tm.getSafe(e);
        // executable.execute(gameMap, gameWorld, e);
    }
}
