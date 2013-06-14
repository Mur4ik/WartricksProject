
package com.wartricks.components;

import com.artemis.Component;

public class EnergyBar extends Component {
    public int maxEnergy, currentEnergy, modifierEnergy;

    public EnergyBar(int maxEnergy) {
        super();
        this.maxEnergy = maxEnergy;
        currentEnergy = maxEnergy;
        modifierEnergy = 0;
    }
}
