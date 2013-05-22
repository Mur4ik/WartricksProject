
package com.wartricks.utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.math.MathUtils;
import com.wartricks.components.Bounds;
import com.wartricks.components.ColorAnimation;
import com.wartricks.components.Expires;
import com.wartricks.components.Health;
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
        e.addComponent(new Health(100));
        e.addComponent(new Bounds(40));
        world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_SHIP);
        return e;
    }

    public static Entity createEnemy(World world, String name, Sprite.Layer layer, float x,
            float y, float vx, float vy) {
        final Entity e = world.createEntity();
        final Position position = new Position();
        position.x = x;
        position.y = y;
        e.addComponent(position);
        final Sprite sprite = new Sprite();
        sprite.name = name;
        sprite.r = 255 / 255f;
        sprite.g = 0 / 255f;
        sprite.b = 142 / 255f;
        sprite.layer = layer;
        e.addComponent(sprite);
        final Velocity velocity = new Velocity(vx, vy);
        e.addComponent(velocity);
        e.addComponent(new Expires(20));
        e.addComponent(new Health(20));
        final Bounds bounds = new Bounds();
        if ("den".equals(sprite.name)) {
            bounds.radius = 200 * sprite.scaleX;
        } else {
            bounds.radius = 40 * sprite.scaleX;
        }
        e.addComponent(bounds);
        world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY_SHIPS);
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
        e.addComponent(new Bounds(10));
        world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_BULLETS);
        return e;
    }

    public static Entity createParticle(World world, float x, float y, float delay) {
        final Entity e = world.createEntity();
        final Position position = new Position();
        position.x = x;
        position.y = y;
        e.addComponent(position);
        final Sprite sprite = new Sprite();
        sprite.name = "troll";
        sprite.scaleX = sprite.scaleY = MathUtils.random(0.3f, 0.6f);
        sprite.r = 1;
        sprite.g = 216 / 255f;
        sprite.b = 0;
        sprite.a = 0.5f;
        sprite.layer = Sprite.Layer.PARTICLES;
        e.addComponent(sprite);
        final Velocity velocity = new Velocity(MathUtils.random(-400, 400), MathUtils.random(-400,
                400));
        e.addComponent(velocity);
        final Expires expires = new Expires();
        expires.delay = delay;
        e.addComponent(expires);
        final ColorAnimation colorAnimation = new ColorAnimation();
        colorAnimation.alphaAnimate = true;
        colorAnimation.alphaSpeed = -1f;
        colorAnimation.alphaMin = 0f;
        colorAnimation.alphaMax = 1f;
        colorAnimation.repeat = false;
        e.addComponent(colorAnimation);
        return e;
    }
}
