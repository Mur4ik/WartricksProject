
package com.wartricks.components;

import com.artemis.Component;

public class Initiative extends Component {
    public int baseInitiative, modifierInitiative;

    public Initiative(int baseInitiative) {
        super();
        this.baseInitiative = baseInitiative;
        modifierInitiative = 0;
    }
}
