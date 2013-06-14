
package com.wartricks.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.Array;

public class SkillSet extends Component {
    public Array<String> skillSet;

    public SkillSet(Array<String> skillSet) {
        super();
        this.skillSet = skillSet;
    }
}
