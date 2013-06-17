
package com.wartricks.components;

import com.artemis.Component;

public class Initiative extends Component {
    private int initiativeBase;

    private int initiativeModifier;

    public Initiative(int baseInitiative) {
        super();
        this.initiativeBase = baseInitiative;
        this.setModifierInitiative(0);
    }

    public int getInitiativeAfterModifiers() {
        return this.getBaseInitiative() + this.getModifierInitiative();
    }

    public int getBaseInitiative() {
        return initiativeBase;
    }

    public int getModifierInitiative() {
        return initiativeModifier;
    }

    public void setModifierInitiative(int modifierInitiative) {
        this.initiativeModifier = modifierInitiative;
    }
}
