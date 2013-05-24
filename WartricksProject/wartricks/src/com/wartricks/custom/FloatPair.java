
package com.wartricks.custom;

public class FloatPair {
    public FloatPair(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x, y;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FloatPair) {
            return this.equals((FloatPair)obj);
        }
        return false;
    }

    public boolean equals(FloatPair p) {
        return ((x == p.x) && (y == p.y));
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
