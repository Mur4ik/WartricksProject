
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.Pair;

public class Action extends Component {
    public int creatureId;

    public int skillId;

    public Pair origin;

    public Pair target;

    public Action(int creatureId, int skillId, int originX, int originY, int targetX, int targetY) {
        origin = new Pair(originX, originY);
        target = new Pair(targetX, targetY);
        this.skillId = skillId;
        this.creatureId = creatureId;
    }

    public Action(int creatureId, int skillId, Pair originPar, Pair destinationPar) {
        origin = originPar;
        target = destinationPar;
        this.skillId = skillId;
        this.creatureId = creatureId;
    }
}
