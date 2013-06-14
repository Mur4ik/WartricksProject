
package com.wartricks.components;

import com.artemis.Component;

public class Cooldown extends Component {
    int maxCooldown, currentCooldown, modifierMaxCooldown, modifierRegenCooldown;

    public Cooldown(int maxCooldown) {
        super();
        this.maxCooldown = maxCooldown;
        currentCooldown = maxCooldown;
        modifierMaxCooldown = 0;
        modifierRegenCooldown = 0;
    }
}
