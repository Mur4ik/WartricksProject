
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.wartricks.components.OnBeginTurn;
import com.wartricks.logic.VersusGame;

public class OnBeginTurnSystem extends EntityProcessingSystem {
    private VersusGame game;

    @Mapper
    ComponentMapper<OnBeginTurn> tm;

    @SuppressWarnings("unchecked")
    public OnBeginTurnSystem(VersusGame game) {
        super(Aspect.getAspectForAll(OnBeginTurn.class));
        this.game = game;
    }

    @Override
    protected void process(Entity e) {
        // final OnBeginTurn executable = tm.getSafe(e);
        // executable.execute(gameMap, gameWorld, e);
    }
}
