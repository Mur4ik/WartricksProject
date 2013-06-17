
package com.wartricks.components;

import com.artemis.Component;

public class Health extends Component {
    private float currentHealth;

    private float maxHealthBase;

    private float maxHealthModifier;

    public Health(float health, float maxHealth) {
        currentHealth = health;
        maxHealthBase = maxHealth;
    }

    public Health(float health) {
        this(health, health);
    }

    public Health() {
        this(0, 0);
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getMaxHealthBase() {
        return maxHealthBase;
    }

    public float getMaxHealthModifier() {
        return maxHealthModifier;
    }

    public void setMaxHealthModifier(float maxHealthBaseModifier) {
        maxHealthModifier = maxHealthBaseModifier;
    }

    public float getMaxHealthAfterModifiers() {
        return maxHealthModifier + maxHealthBase;
    }
}
