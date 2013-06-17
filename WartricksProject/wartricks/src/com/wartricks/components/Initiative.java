
package com.wartricks.components;

import com.artemis.Component;

public class Initiative extends Component {
    private int initiativeBase;

    private int initiativeModifier;

    public Initiative(int baseInitiative) {
        super();
        initiativeBase = baseInitiative;
        this.setInitiativeModifier(0);
    }

    public int getInitiativeAfterModifiers() {
        return this.getInitiativeBase() + this.getInitiativeModifier();
    }

    public int getInitiativeBase() {
        return initiativeBase;
    }

    public int getInitiativeModifier() {
        return initiativeModifier;
    }

    public void setInitiativeModifier(int modifierInitiative) {
        initiativeModifier = modifierInitiative;
    }
}
