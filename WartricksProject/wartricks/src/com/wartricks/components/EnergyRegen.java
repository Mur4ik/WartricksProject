
package com.wartricks.components;

import com.artemis.Component;

public class EnergyRegen extends Component {
    public int regen, modifierRegen;

    public EnergyRegen(int regen) {
        super();
        this.regen = regen;
        modifierRegen = 0;
    }
}
