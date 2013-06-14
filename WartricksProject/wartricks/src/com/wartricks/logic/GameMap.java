
package com.wartricks.logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;
import com.wartricks.custom.Pair;
import com.wartricks.custom.PositionArray;
import com.wartricks.utils.BoardGenerator;
import com.wartricks.utils.MapTools;

public class GameMap {
    private int[][] map;

    private int[][] entityByCoord;

    public int width, height;

    public int colSize, rowSize;

    private Pixmap pixmap;

    public Texture texture;

    public MapTools tools;

    private ObjectMap<Integer, Pair> coordByEntity;

    public final PositionArray highlighted;

    private OrthographicCamera gameCamera;

    public GameMap(int columnSize, int rwSize, int mapWidth, int mapHeight,
            OrthographicCamera camera) {
        map = BoardGenerator.getMap(mapWidth, mapHeight);
        width = map.length;
        height = map[0].length;
        colSize = columnSize;
        rowSize = rwSize;
        entityByCoord = new int[width][height];
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixmap.setColor(this.getColor(map[i][j]));
                pixmap.drawPixel(i, j);
                entityByCoord[i][j] = -1;
            }
        }
        texture = new Texture(pixmap);
        pixmap.dispose();
        gameCamera = camera;
        tools = new MapTools("hex", this, gameCamera);
        coordByEntity = new ObjectMap<Integer, Pair>();
        highlighted = new PositionArray(this);
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

    public int getEntityAt(int x, int y) {
        return entityByCoord[x][y];
    }

    public boolean cellOccupied(int x, int y) {
        return (entityByCoord[x][y] > -1);
    }

    public Pair getCoordinatesFor(int entityId) {
        if (coordByEntity.containsKey(entityId)) {
            return coordByEntity.get(entityId);
        }
        return null;
    }

    public void addEntity(int id, int x, int y) {
        entityByCoord[x][y] = id;
        coordByEntity.put(id, new Pair(x, y));
    }

    public void moveEntity(int id, int x, int y) {
        final Pair old = coordByEntity.put(id, new Pair(x, y));
        entityByCoord[old.x][old.y] = -1;
        entityByCoord[x][y] = id;
    }

    public int getPositionAt(int x, int y) {
        return map[x][y];
    }

    public void addHighlights(Pair... pairs) {
        if (pairs.length > 0) {
            for (final Pair pair : pairs) {
                if (!highlighted.contains(pair, false)) {
                    highlighted.add(pair);
                }
            }
        }
    }

    public void addHighlights(PositionArray pairs) {
        if (pairs.size > 0) {
            for (final Pair pair : pairs) {
                if (!highlighted.contains(pair, false)) {
                    highlighted.add(pair);
                }
            }
        }
    }

    public void removeHighlights(Pair... pairs) {
        if (pairs.length > 0) {
            for (final Pair pair : pairs) {
                if (!highlighted.contains(pair, false)) {
                    highlighted.removeValue(pair, false);
                }
            }
        }
    }

    public void clearHighlights() {
        highlighted.clear();
    }
}
