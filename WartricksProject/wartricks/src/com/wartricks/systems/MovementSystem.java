
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.wartricks.components.Position;
import com.wartricks.components.Velocity;

public class MovementSystem extends EntityProcessingSystem {
    @Mapper
    ComponentMapper<Position> pm;

    @Mapper
    ComponentMapper<Velocity> vm;

    @SuppressWarnings("unchecked")
    public MovementSystem() {
        super(Aspect.getAspectForAll(Position.class, Velocity.class));
    }

    @Override
    protected void process(Entity e) {
        final Position position = pm.get(e);
        final Velocity velocity = vm.get(e);
        position.x += velocity.vx * world.delta;
        position.y += velocity.vy * world.delta;
    }
}
