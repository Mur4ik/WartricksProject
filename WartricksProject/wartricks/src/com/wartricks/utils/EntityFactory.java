
package com.wartricks.utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.math.MathUtils;
import com.wartricks.components.Bounds;
import com.wartricks.components.ColorAnimation;
import com.wartricks.components.Expires;
import com.wartricks.components.Health;
import com.wartricks.components.Label;
import com.wartricks.components.MapPosition;
import com.wartricks.components.Path;
import com.wartricks.components.Position;
import com.wartricks.components.ScaleAnimation;
import com.wartricks.components.Sprite;
import com.wartricks.components.Velocity;

public class EntityFactory {
    public static Entity createCharacter(World world, String sprite, float x, float y) {
        final Entity e = world.createEntity();
        e.addComponent(new MapPosition(x, y));
        e.addComponent(new Sprite(sprite, Sprite.Layer.ACTORS_3));
        e.addComponent(new Velocity());
        e.addComponent(new Health(100));
        e.addComponent(new Bounds(40));
        e.addComponent(new Path());
        world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
        world.getManager(TagManager.class).register(sprite, e);
        return e;
    }

    public static Entity createEnemy(World world, String name, Sprite.Layer layer, float x,
            float y, float vx, float vy) {
        final Entity e = world.createEntity();
        final MapPosition position = new MapPosition();
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
        final MapPosition position = new MapPosition();
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

    public static Entity createLabel(World world, String name, String text, int x, int y) {
        final Entity e = world.createEntity();
        final Label label = new Label(text);
        final Position position = new Position(x, y);
        e.addComponent(label);
        e.addComponent(position);
        world.getManager(TagManager.class).register(name, e);
        return e;
    }

    public static Entity createClick(World world, int x, int y, float startScale, float speed,
            float expiration) {
        final Entity e = world.createEntity();
        e.addComponent(new MapPosition(x, y));
        final Sprite sprite = new Sprite("kirby", Sprite.Layer.ACTORS_3);
        sprite.r = 1f;
        sprite.g = 1f;
        sprite.b = 1f;
        sprite.a = 0.5f;
        sprite.rotation = 0f;
        sprite.scaleX = startScale;
        sprite.scaleY = startScale;
        e.addComponent(sprite);
        final Expires expires = new Expires(expiration);
        e.addComponent(expires);
        final ScaleAnimation scaleAnimation = new ScaleAnimation(speed);
        e.addComponent(scaleAnimation);
        final ColorAnimation colorAnimation = new ColorAnimation();
        colorAnimation.alphaAnimate = false;
        colorAnimation.alphaSpeed = -1f;
        colorAnimation.alphaMin = 0f;
        colorAnimation.alphaMax = 1f;
        colorAnimation.repeat = false;
        e.addComponent(colorAnimation);
        return e;
    }
}
