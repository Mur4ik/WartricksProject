
package com.wartricks.utils;

import com.artemis.Entity;
import com.artemis.World;
import com.wartricks.components.Expires;
import com.wartricks.components.Player;
import com.wartricks.components.Position;
import com.wartricks.components.Sprite;
import com.wartricks.components.Velocity;

public class EntityFactory {
    public static Entity createPlayer(World world, float x, float y) {
        final Entity e = world.createEntity();
        e.addComponent(new Position(x, y));
        e.addComponent(new Sprite("sprites/dash.png"));
        e.addComponent(new Velocity());
        e.addComponent(new Player());
        return e;
    }

    public static Entity createEnemy(World world, float x, float y, String sprite) {
        final Entity e = world.createEntity();
        e.addComponent(new Position(x, y));
        e.addComponent(new Sprite(sprite));
        return e;
    }

    public static Entity createBullet(World world, float x, float y) {
        final Entity e = world.createEntity();
        e.addComponent(new Position(x, y));
        e.addComponent(new Sprite("sprites/pinkie.png"));
        e.addComponent(new Velocity(0, 800));
        e.addComponent(new Expires(1f));
        return e;
    }
}
