
package com.wartricks.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;

public class SkillSet extends Component {
    public Array<Integer> skillSet;

    public SkillSet(Array<Integer> skillSet) {
        super();
        this.skillSet = skillSet;
    }
}
