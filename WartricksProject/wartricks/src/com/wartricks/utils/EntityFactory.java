
package com.wartricks.utils;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.MathUtils;
import com.wartricks.components.Expires;
import com.wartricks.components.Player;
import com.wartricks.components.Position;
import com.wartricks.components.Sprite;
import com.wartricks.components.Velocity;

public class EntityFactory {
    public static Entity createPlayer(World world, float x, float y) {
        final Entity e = world.createEntity();
        e.addComponent(new Position(x, y));
        e.addComponent(new Sprite("dash", Sprite.Layer.ACTORS_3));
        e.addComponent(new Velocity());
        e.addComponent(new Player());
        return e;
    }

    public static Entity createEnemy(World world, String name, Sprite.Layer layer, float x,
            float y, float vx, float vy) {
        final Entity e = world.createEntity();
        final Position position = new Position();
        position.x = x;
        position.y = y;
        e.addComponent(position);
        final Sprite sprite = new Sprite("apple");
        sprite.name = name;
        sprite.r = 255 / 255f;
        sprite.g = 0 / 255f;
        sprite.b = 142 / 255f;
        sprite.layer = layer;
        e.addComponent(sprite);
        final Velocity velocity = new Velocity(vx, vy);
        e.addComponent(velocity);
        return e;
    }

    public static Entity createBullet(World world, float x, float y) {
        final Entity e = world.createEntity();
        e.addComponent(new Position(x, y));
        final Sprite bulletSprite = new Sprite("pinkie", Sprite.Layer.PARTICLES);
        bulletSprite.r = MathUtils.random(1f);
        bulletSprite.g = MathUtils.random(1f);
        bulletSprite.b = MathUtils.random(1f);
        e.addComponent(bulletSprite);
        e.addComponent(new Velocity(0, 800));
        e.addComponent(new Expires(2f));
        return e;
    }
}
