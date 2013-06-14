
package com.wartricks.components;

import com.artemis.Component;
import com.wartricks.custom.Pair;

public class Action extends Component {
    public String skillName;

    public Pair origin;

    public Pair target;

    public Action(String skillName, int originX, int originY, int targetX, int targetY) {
        origin = new Pair(originX, originY);
        target = new Pair(targetX, targetY);
        this.skillName = skillName;
    }

    public Action(String skillName, Pair originPar, Pair destinationPar) {
        origin = originPar;
        target = destinationPar;
        this.skillName = skillName;
    }
}