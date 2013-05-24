
package com.wartricks.custom;

public class MyMath {
    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    public static int min(int a, int b, int c) {
        if (min(a, b) < c) {
            return min(a, b);
        }
        return c;
    }

    public static int pow(int a, int b) {
        if (b > 1) {
            return a * pow(a, b - 1);
        } else if (b == 1) {
            return a;
        } else {
            return 1;
        }
    }
}
