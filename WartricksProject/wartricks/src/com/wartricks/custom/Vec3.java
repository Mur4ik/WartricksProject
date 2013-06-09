
package com.wartricks.custom;

public class Vec3 {
    public int x, y, z;

    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vec3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(int[] values) {
        x = 0;
        y = 0;
        z = 0;
        final int length = values.length;
        if (length > 0) {
            x = values[0];
        }
        if (length > 1) {
            y = values[1];
        }
        if (length > 2) {
            z = values[2];
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == Vec3.class) {
            final Vec3 vec3 = (Vec3)obj;
            if ((vec3.x == x) && (vec3.y == y) && (vec3.z == z)) {
                return true;
            }
        }
        return false;
    }
}
