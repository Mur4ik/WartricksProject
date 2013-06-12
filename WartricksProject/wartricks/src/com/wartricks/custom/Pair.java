
package com.wartricks.custom;

public class Pair {
    public Pair() {
        x = 0;
        y = 0;
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair(int[] values) {
        x = 0;
        y = 0;
        final int length = values.length;
        if (length > 0) {
            x = values[0];
        }
        if (length > 1) {
            y = values[1];
        }
    }

    public Pair(Pair pair) {
        x = pair.x;
        y = pair.y;
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
