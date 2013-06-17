
package com.wartricks.components;

import com.artemis.Component;

public class Cooldown extends Component {
    private int maxCooldownBase;

    private int currentCooldown;

    private int maxCooldownModifier;

    private int modifierRegenCooldown;

    public Cooldown(int maxCooldown) {
        super();
        maxCooldownBase = maxCooldown;
        this.setCurrentCooldown(maxCooldown);
        this.setMaxCooldownModifier(0);
        this.setModifierRegenCooldown(1);
    }

    public int getModifierRegenCooldown() {
        return modifierRegenCooldown;
    }

    public void setModifierRegenCooldown(int modifierRegenCooldown) {
        this.modifierRegenCooldown = modifierRegenCooldown;
    }

    public int refreshOnce() {
        this.setCurrentCooldown(this.getCurrentCooldown() + this.getMaxCooldownModifier());
        return this.getCurrentCooldown();
    }

    public int getMaxCooldownBase() {
        return maxCooldownBase;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
        if (currentCooldown > this.getMaxCooldownAfterModifiers()) {
            currentCooldown = this.getMaxCooldownAfterModifiers();
        }
    }

    public int getMaxCooldownModifier() {
        return maxCooldownModifier;
    }

    public void setMaxCooldownModifier(int maxCooldownModifier) {
        this.maxCooldownModifier = maxCooldownModifier;
    }

    public boolean isReady() {
        return currentCooldown >= this.getMaxCooldownAfterModifiers();
    }

    public int modifyCooldownBy(int value) {
        currentCooldown -= value;
        if (currentCooldown > this.getMaxCooldownAfterModifiers()) {
            currentCooldown = this.getMaxCooldownAfterModifiers();
        }
        return currentCooldown;
    }

    public int getMaxCooldownAfterModifiers() {
        return maxCooldownBase + maxCooldownModifier;
    }
}
