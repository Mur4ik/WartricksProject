
package com.wartricks.custom;

public class Pair {
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x, y;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Pair.class) {
            final Pair pair = (Pair)obj;
            if ((pair.x == x) && (pair.y == y)) {
                return true;
            }
        }
        return false;
    }
}
