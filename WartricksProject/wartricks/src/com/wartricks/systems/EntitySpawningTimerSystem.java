
package com.wartricks.systems;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.badlogic.gdx.math.MathUtils;
import com.wartricks.components.Sprite;
import com.wartricks.utils.EntityFactory;
import com.wartricks.utils.LoadScript;

public class EntitySpawningTimerSystem extends VoidEntitySystem {
    private Timer timer1;

    private Timer timer2;

    private Timer timer3;

    public EntitySpawningTimerSystem() {
        timer1 = new Timer(2, true) {
            @Override
            public void execute() {
                final LoadScript enemyScript = new LoadScript("characters/applejack.lua");
                enemyScript.runUnboundScriptFunction("create", EntityFactory.class, world,
                        Sprite.Layer.ACTORS_3);
                // EntityFactory.createEnemy(world, "apple", Sprite.Layer.ACTORS_3,
                // MathUtils.random(0, 1080), 576 + 30, 0, -40).addToWorld();
            }
        };
        timer2 = new Timer(6, true) {
            @Override
            public void execute() {
                EntityFactory.createEnemy(world, "kirby", Sprite.Layer.ACTORS_2,
                        MathUtils.random(0, 1080), 576 + 60, 0, -30).addToWorld();
            }
        };
        timer3 = new Timer(12, true) {
            @Override
            public void execute() {
                EntityFactory.createEnemy(world, "troll", Sprite.Layer.ACTORS_1,
                        MathUtils.random(0, 1080), 576 + 120, 0, -20).addToWorld();
            }
        };
    }

    @Override
    protected void processSystem() {
        timer1.update(world.delta);
        timer2.update(world.delta);
        timer3.update(world.delta);
    }
}
