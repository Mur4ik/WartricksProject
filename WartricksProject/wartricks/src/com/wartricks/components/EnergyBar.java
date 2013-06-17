
package com.wartricks.components;

import com.artemis.Component;

public class EnergyBar extends Component {
    private int maxEnergyBase;

    private int currentEnergy;

    private int maxEnergyModifier;

    public EnergyBar(int maxEnergy) {
        super();
        maxEnergyBase = maxEnergy;
        this.setCurrentEnergy(maxEnergy);
        this.setMaxEnergyModifier(0);
    }

    public int getMaxEnergy() {
        return maxEnergyBase;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
        if (currentEnergy > this.getMaxEnergyAfterModifiers()) {
            this.currentEnergy = this.getMaxEnergyAfterModifiers();
        }
    }

    public int getMaxEnergyModifier() {
        return maxEnergyModifier;
    }

    public void setMaxEnergyModifier(int maxEnergyModifier) {
        this.maxEnergyModifier = maxEnergyModifier;
    }

    public int getMaxEnergyAfterModifiers() {
        return maxEnergyBase + maxEnergyModifier;
    }

    public float modifyCurrentEnergyBy(float value) {
        currentEnergy += value;
        if (currentEnergy > this.getMaxEnergyAfterModifiers()) {
            currentEnergy = this.getMaxEnergyAfterModifiers();
        }
        return currentEnergy;
    }
}
