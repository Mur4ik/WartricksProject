
package com.wartricks.custom;

import com.wartricks.utils.MapTools.Shapes;

public class SkillShape {
    public Shapes shape;

    public int minRange, maxRange;

    public SkillShape(Shapes shape, int minRange, int maxRange) {
        this.shape = shape;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }
}
