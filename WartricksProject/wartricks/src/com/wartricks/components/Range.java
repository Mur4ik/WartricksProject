
package com.wartricks.components;

import com.artemis.Component;

public class Range extends Component {
    private int minRangeBase, maxRangeBase, minRangeModifier, maxRangeModifier;

    public Range(int min, int max) {
        minRangeBase = min;
        maxRangeBase = max;
    }

    public int getMinRangeModifier() {
        return minRangeModifier;
    }

    public void setMinRangeModifier(int minRangeModifier) {
        this.minRangeModifier = minRangeModifier;
    }

    public int getMaxRangeModifier() {
        return maxRangeModifier;
    }

    public void setMaxRangeModifier(int maxRangeModifier) {
        this.maxRangeModifier = maxRangeModifier;
    }

    public int getMinRangeBase() {
        return minRangeBase;
    }

    public int getMaxRangeBase() {
        return maxRangeBase;
    }

    public int getMinRangeAfterModifiers() {
        return minRangeBase + minRangeModifier;
    }

    public int getMaxRangeAfterModifiers() {
        return maxRangeBase + maxRangeModifier;
    }
}
