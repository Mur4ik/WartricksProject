
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.wartricks.components.OnBeginTurn;
import com.wartricks.logic.GameMap;

public class OnBeginTurnSystem extends EntityProcessingSystem {
    private GameMap gameMap;

    private World gameWorld;

    @Mapper
    ComponentMapper<OnBeginTurn> tm;

    @SuppressWarnings("unchecked")
    public OnBeginTurnSystem(GameMap gameMap, World gameWorld) {
        super(Aspect.getAspectForAll(OnBeginTurn.class));
        this.gameMap = gameMap;
        this.gameWorld = gameWorld;
    }

    @Override
    protected void process(Entity e) {
        // final OnBeginTurn executable = tm.getSafe(e);
        // executable.execute(gameMap, gameWorld, e);
    }
}
