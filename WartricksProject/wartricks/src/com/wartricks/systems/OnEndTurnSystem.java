
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.wartricks.components.OnEndTurn;
import com.wartricks.logic.GameMap;

public class OnEndTurnSystem extends EntityProcessingSystem {
    private GameMap gameMap;

    private World gameWorld;

    @Mapper
    ComponentMapper<OnEndTurn> tm;

    @SuppressWarnings("unchecked")
    public OnEndTurnSystem(GameMap gameMap, World gameWorld) {
        super(Aspect.getAspectForAll(OnEndTurn.class));
        this.gameMap = gameMap;
        this.gameWorld = gameWorld;
    }

    @Override
    protected void process(Entity e) {
        // final OnEndTurn executable = tm.getSafe(e);
        // executable.execute(gameMap, gameWorld, e);
    }
}
