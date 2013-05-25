
package com.wartricks.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.wartricks.components.Bounds;
import com.wartricks.components.Health;
import com.wartricks.components.Label;
import com.wartricks.components.Position;
import com.wartricks.utils.Constants;
import com.wartricks.utils.EntityFactory;

public class CollisionSystem extends EntitySystem {
    @Mapper
    ComponentMapper<Position> pm;

    @Mapper
    ComponentMapper<Bounds> bm;

    @Mapper
    ComponentMapper<Health> hm;

    private Bag<CollisionPair> collisionPairs;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Aspect.getAspectForAll(Position.class, Bounds.class));
    }

    @Override
    public void initialize() {
        collisionPairs = new Bag<CollisionPair>();
        collisionPairs.add(new CollisionPair(Constants.Groups.PLAYER_BULLETS,
                Constants.Groups.ENEMY_SHIPS, new CollisionHandler() {
                    @Override
                    public void handleCollision(Entity bullet, Entity ship) {
                        final Health health = hm.get(ship);
                        final Position position = pm.get(ship);
                        health.health -= 10;
                        bullet.deleteFromWorld();
                        if (health.health <= 0) {
                            for (int i = 0; i < 50; i++) {
                                EntityFactory.createParticle(world, (int)position.x,
                                        (int)position.y, 0.6f).addToWorld();
                            }
                            final Label scoreLabel = world.getManager(TagManager.class)
                                    .getEntity("score").getComponent(Label.class);
                            int score = Integer.parseInt(scoreLabel.text);
                            score += 10;
                            scoreLabel.text = Integer.toString(score);
                            ship.deleteFromWorld();
                        } else {
                            EntityFactory.createParticle(world, position.x, position.y, 0.3f)
                                    .addToWorld();
                        }
                    }
                }));
        collisionPairs.add(new CollisionPair(Constants.Groups.PLAYER, Constants.Groups.ENEMY_SHIPS,
                new CollisionHandler() {
                    @Override
                    public void handleCollision(Entity player, Entity ship) {
                        final Health health = hm.get(player);
                        final Position position = pm.get(player);
                        health.health -= 10;
                        final Label lifeLabel = world.getManager(TagManager.class)
                                .getEntity("life").getComponent(Label.class);
                        lifeLabel.text = Integer.toString((int)health.health);
                        ship.deleteFromWorld();
                        if (health.health <= 0) {
                            EntityFactory.createParticle(world, position.x, position.y, 0.6f)
                                    .addToWorld();
                            player.deleteFromWorld();
                            Gdx.app.exit();
                        }
                    }
                }));
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for (int i = 0; collisionPairs.size() > i; i++) {
            collisionPairs.get(i).checkForCollisions();
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    private class CollisionPair {
        private ImmutableBag<Entity> groupEntitiesA;

        private ImmutableBag<Entity> groupEntitiesB;

        private CollisionHandler handler;

        public CollisionPair(String group1, String group2, CollisionHandler handler) {
            groupEntitiesA = world.getManager(GroupManager.class).getEntities(group1);
            groupEntitiesB = world.getManager(GroupManager.class).getEntities(group2);
            this.handler = handler;
        }

        public void checkForCollisions() {
            for (int a = 0; groupEntitiesA.size() > a; a++) {
                for (int b = 0; groupEntitiesB.size() > b; b++) {
                    final Entity entityA = groupEntitiesA.get(a);
                    final Entity entityB = groupEntitiesB.get(b);
                    if (this.collisionExists(entityA, entityB)) {
                        handler.handleCollision(entityA, entityB);
                    }
                }
            }
        }

        private boolean collisionExists(Entity e1, Entity e2) {
            final Position p1 = pm.get(e1);
            final Position p2 = pm.get(e2);
            final Bounds b1 = bm.get(e1);
            final Bounds b2 = bm.get(e2);
            return Utils.doCirclesCollide(p1.x, p1.y, b1.radius, p2.x, p2.y, b2.radius);
            // return Utils.distance(p1.x, p1.y, p2.x, p2.y)-b1.radius < b2.radius;
        }
    }

    private interface CollisionHandler {
        void handleCollision(Entity a, Entity b);
    }
}
