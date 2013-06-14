
package com.wartricks.components;

import com.artemis.Component;

public class Range extends Component {
    public int minRange, maxRange, modifierMinRange, modifierMaxRange;

    public Range(int min, int max) {
        minRange = min;
        maxRange = max;
    }
}
