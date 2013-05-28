
package com.wartricks.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.wartricks.custom.FloatPair;
import com.wartricks.custom.MyMath;
import com.wartricks.custom.Pair;

public class MapTools {
    public static int col_multiple = 0;

    public static int row_multiple = 0;

    public static String name = "hex";

    private static MapTools mapTools;

    public static MapTools initialize(int columnSize, int rowSize) {
        return initialize(columnSize, rowSize, name);
    }

    public static MapTools initialize(int columnSize, int rowSize, String regionName) {
        if ((columnSize <= 0) || (rowSize <= 0)) {
            throw new ExceptionInInitializerError("Value of row and column must be positive.");
        }
        if ((null == mapTools) || (col_multiple != columnSize) || (row_multiple != rowSize)) {
            mapTools = new MapTools(columnSize, rowSize, regionName);
        }
        return mapTools;
    }

    private MapTools(int columnSize, int rowSize, String regionName) {
        col_multiple = columnSize;
        row_multiple = rowSize;
        name = regionName;
    }

    public Array<Pair> getNeighbors(int x, int y, int range, int[][] map) {
        final Array<Pair> coordinates = new Array<Pair>();
        int min;
        int myrow;
        for (int row = y - range; row < (y + range + 1); row++) {
            min = MyMath.min(2 * ((row - y) + range), range, (-2 * (row - y - range)) + 1);
            for (int col = x - min; col < (x + min + 1); col++) {
                if ((col < 0) || (col >= map.length)) {
                    continue;
                }
                if ((x == col) && (y == row)) {
                    continue;
                } else if ((x % 2) == 0) {
                    myrow = (2 * y) - row;
                } else {
                    myrow = row;
                }
                if ((myrow < 0) || (myrow >= map[0].length)) {
                    continue;
                }
                coordinates.add(new Pair(col, myrow));
            }
        }
        return coordinates;
    }

    public Array<Pair> getNeighbors(int x, int y, int[][] map) {
        return this.getNeighbors(x, y, 1, map);
    }

    public int distance(int x0, int y0, int x1, int y1) {
        final int dx = Math.abs(x1 - x0);
        final int dy = Math.abs(y1 - y0);
        // The distance can be tricky, because of how the columns are shifted.
        // Different cases must be considered, because the dx and dy above
        // are not sufficient to determine distance.
        if (((dx) % 2) == 0) { // distance from even->even or odd->odd column
                               // important to know since evens and odds are offset
            return MyMath.max(dx, (dx / 2) + dy);
        }
        // Otherwise the distance must be even->odd
        else if ((((x0 % 2) == 0) && (y0 > y1)) || (((x1 % 2) == 0) && (y1 > y0))) { // even on top
            return MyMath.max(dx, ((dx - 1) / 2) + dy);
        }
        // otherwise odd must be on top
        return MyMath.max(dx, ((dx + 1) / 2) + dy);
    }

    public Pair window2world(float x, float y, OrthographicCamera camera) {
        final Vector3 pos = new Vector3(x, y, 0);
        camera.unproject(pos);
        final int posx = (int)((pos.x - 6f) / col_multiple);
        final int posy = (int)((pos.y - (((float)row_multiple * (posx % 2)) / 2)) / row_multiple);
        return new Pair(posx, posy);
    }

    public Pair libgdx2world(float x, float y) {
        final Vector3 pos = new Vector3(x, y, 0);
        final int posx = (int)((pos.x - 6f) / col_multiple);
        final int posy = (int)((pos.y - (((float)row_multiple * (posx % 2)) / 2)) / row_multiple);
        return new Pair(posx, posy);
    }

    public FloatPair world2window(float x, float y) {
        final int x0 = (int)x;
        final float dx = x - x0; // purely the decimal part
        final float posX = 5.5f + ((x + 0.5f) * col_multiple);
        final float posY = row_multiple
                * (y + 0.5f + ((x0 % 2) * (0.5f - (dx / 2f))) + ((((x0 + 1) % 2) * dx) / 2f));
        return new FloatPair(posX, posY);
    }

    public FloatPair getDirectionVector(int x1, int y1, int x2, int y2) {
        final FloatPair cell1 = this.world2window(x1, y1);
        final FloatPair cell2 = this.world2window(x2, y2);
        return new FloatPair(cell2.x - cell1.x, cell2.y - cell1.y);
    }
}
