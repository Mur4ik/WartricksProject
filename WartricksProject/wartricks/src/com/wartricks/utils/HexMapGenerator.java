
package com.wartricks.utils;


public class HexMapGenerator {
    public HexMapGenerator() {
    }

    public int[][] getDiamondSquare() {
        final MidpointDisplacement md = new MidpointDisplacement();
        return md.getMap();
    }
}
