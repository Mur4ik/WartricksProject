
package com.wartricks.components;

import com.artemis.Component;

public class Cost extends Component {
    private int costBase;

    private int costModifier;

    public Cost(int baseCost) {
        super();
        costBase = baseCost;
        this.setCostModifier(0);
    }

    public int getCostAfterModifiers() {
        return this.getCostBase() + this.getCostModifier();
    }

    public int getCostBase() {
        return costBase;
    }

    public int getCostModifier() {
        return costModifier;
    }

    public void setCostModifier(int costModifier) {
        this.costModifier = costModifier;
    }
}
