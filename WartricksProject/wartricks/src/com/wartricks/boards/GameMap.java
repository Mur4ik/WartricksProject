
package com.wartricks.boards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.wartricks.utils.BoardGenerator;

public class GameMap {
    public int[][] map;

    public int width, height;

    public Pixmap pixmap;

    public Texture texture;

    public GameMap() {
        map = BoardGenerator.getMap(10, 7);
        width = map.length;
        height = map[0].length;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixmap.setColor(this.getColor(map[i][j]));
                pixmap.drawPixel(i, j);
            }
        }
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    private Color getColor(int color) { // r g b
        if (color == 0) {
            return myColor(34, 53, 230);
        } else if (color == 1) {
            return myColor(105, 179, 239);
        } else if (color == 2) {
            return myColor(216, 209, 129);
        } else if (color == 3) {
            return myColor(183, 245, 99);
        } else if (color == 4) {
            return myColor(109, 194, 46);
        } else if (color == 5) {
            return myColor(87, 155, 36);
        } else if (color == 6) {
            return myColor(156, 114, 35);
        } else if (color == 7) {
            return myColor(135, 48, 5);
        } else {
            return new Color(1, 1, 1, 1);
        }
    }

    private static Color myColor(int r, int g, int b) {
        return new Color(r / 255f, g / 255f, b / 255f, 1);
    }
}
