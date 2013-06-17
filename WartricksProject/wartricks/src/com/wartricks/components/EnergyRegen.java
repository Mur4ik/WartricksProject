
package com.wartricks.components;

import com.artemis.Component;

public class EnergyRegen extends Component {
    private int regenBase, regenModifier;

    public EnergyRegen(int regen) {
        super();
        regenBase = regen;
        setRegenModifier(0);
    }

    public int getEnergyRegenAfterModifiers() {
        return regenBase + getRegenModifier();
    }

    public int getRegenBase() {
        return regenBase;
    }

    private int getRegenModifier() {
        return regenModifier;
    }

    private void setRegenModifier(int regenModifier) {
        this.regenModifier = regenModifier;
    }
}
