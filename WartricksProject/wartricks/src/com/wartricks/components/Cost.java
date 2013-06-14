
package com.wartricks.components;

import com.artemis.Component;

public class Cost extends Component {
    public int baseCost, modifierCost;

    public Cost(int baseCost) {
        super();
        this.baseCost = baseCost;
        modifierCost = 0;
    }
}
