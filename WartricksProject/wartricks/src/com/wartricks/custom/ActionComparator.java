
package com.wartricks.custom;

import java.util.Comparator;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.wartricks.components.Action;
import com.wartricks.components.Initiative;

public class ActionComparator implements Comparator<Action> {
    private World world;

    @Mapper
    ComponentMapper<Initiative> im;

    public ActionComparator(World world) {
        super();
        this.world = world;
        im = world.getMapper(Initiative.class);
    }

    @Override
    public int compare(Action action1, Action action2) {
        final Initiative init1 = im.get(world.getEntity(action1.skillId));
        final Initiative init2 = im.get(world.getEntity(action2.skillId));
        if (init1.getCalculatedInitiative() > init2.getCalculatedInitiative()) {
            return 1;
        } else if (init1.getCalculatedInitiative() < init2.getCalculatedInitiative()) {
            return -1;
        } else {
            return 0;
        }
    }
}
