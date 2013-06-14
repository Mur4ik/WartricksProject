
package com.wartricks.components;

import com.artemis.Component;

public class EnergyCounter extends Component {
    int maxEnergy, currentEnergy, modifierEnergy;

    public EnergyCounter(int maxEnergy) {
        super();
        this.maxEnergy = maxEnergy;
        currentEnergy = maxEnergy;
        modifierEnergy = 0;
    }
}
