
package com.wartricks.utils;

public class BoardGenerator {
    public static int[][] getMap(int width, int height) {
        return new int[width][height];
    }

    public static int[][] getRandomMap(int width, int height, int pot) {
        final MidpointDisplacement md = new MidpointDisplacement(width, height, pot);
        return md.getMap();
    }
}
