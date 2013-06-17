
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
        setCurrentCooldown(maxCooldown);
        setMaxCooldownModifier(0);
        this.setModifierRegenCooldown(0);
    }

    public int getModifierRegenCooldown() {
        return modifierRegenCooldown;
    }

    public void setModifierRegenCooldown(int modifierRegenCooldown) {
        this.modifierRegenCooldown = modifierRegenCooldown;
    }

    public int refreshOnce() {
        setCurrentCooldown(getCurrentCooldown() + (1 + getMaxCooldownModifier()));
        return getCurrentCooldown();
    }

    public int getMaxCooldownBase() {
        return maxCooldownBase;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }

    public int getMaxCooldownModifier() {
        return maxCooldownModifier;
    }

    public void setMaxCooldownModifier(int maxCooldownModifier) {
        this.maxCooldownModifier = maxCooldownModifier;
    }
}
