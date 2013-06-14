
package com.wartricks.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.wartricks.custom.FloatPair;
import com.wartricks.custom.MyMath;
import com.wartricks.custom.Pair;
import com.wartricks.custom.PositionArray;
import com.wartricks.custom.Vec3;
import com.wartricks.logic.GameMap;

public class MapTools {
    public String name;

    private GameMap gameMap;

    private OrthographicCamera gameCamera;

    public MapTools(String regionName, GameMap map, OrthographicCamera camera) {
        name = regionName;
        gameMap = map;
        gameCamera = camera;
    }

    public PositionArray getNeighbors(Pair origin, int range) {
        return this.getNeighbors(origin.x, origin.y, range);
    }

    public PositionArray getNeighbors(int x, int y, int range) {
        final PositionArray coordinates = new PositionArray(gameMap);
        int min;
        int myrow;
        for (int row = y - range; row < (y + range + 1); row++) {
            min = MyMath.min(2 * ((row - y) + range), range, (-2 * (row - y - range)) + 1);
            for (int col = x - min; col < (x + min + 1); col++) {
                if ((col < 0) || (col >= gameMap.width)) {
                    continue;
                }
                if ((x == col) && (y == row)) {
                    continue;
                } else if ((x % 2) == 0) {
                    myrow = (2 * y) - row;
                } else {
                    myrow = row;
                }
                if ((myrow < 0) || (myrow >= gameMap.height)) {
                    continue;
                }
                coordinates.add(col, myrow);
            }
        }
        return coordinates;
    }

    public PositionArray getNeighbors(int x, int y) {
        return this.getNeighbors(x, y, 1);
    }

    public int getDistance(int x0, int y0, int x1, int y1) {
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

    public Pair window2world(float x, float y) {
        final Vector3 pos = new Vector3(x, y, 0);
        gameCamera.unproject(pos);
        float posx = ((pos.x - 6f) / gameMap.colSize);
        float posy = (((pos.y + 8f) - ((gameMap.rowSize * (posx % 2)) / 2)) / gameMap.rowSize);
        // Avoids bug in range (0, -1) where it would round to 0
        if (posx < 0) {
            posx -= 1;
        }
        if ((posy < 0)) {
            posy -= 1;
        }
        return new Pair((int)posx, (int)posy);
    }

    public Pair libgdx2world(float x, float y) {
        final Vector3 pos = new Vector3(x, y, 0);
        float posx = ((pos.x - 6f) / gameMap.colSize);
        float posy = (((pos.y) - ((gameMap.rowSize * (posx % 2)) / 2)) / gameMap.rowSize);
        // Avoids bug in range (0, -1) where it would round to 0
        if (posx < 0) {
            posx -= 1;
        }
        if ((posy < 0)) {
            posy -= 1;
        }
        return new Pair((int)posx, (int)posy);
    }

    public FloatPair world2window(float x, float y) {
        final int x0 = (int)x;
        final float dx = x - x0; // purely the decimal part
        final float posX = 5.5f + ((x + 0.5f) * gameMap.colSize);
        final float posY = gameMap.rowSize
                * (y + 0.5f + ((x0 % 2) * (0.5f - (dx / 2f))) + ((((x0 + 1) % 2) * dx) / 2f));
        return new FloatPair(posX, posY);
    }

    public Pair getAtRelativePosition(Pair origin, Pair amount) {
        origin.x += amount.x;
        origin.y += amount.y;
        return origin;
    }

    public Pair getAtRelativePosition(Pair origin, FloatPair direction, int distance) {
        final Vec3 cubeCoord = coordOffset2Cube(origin.x, origin.y);
        if (direction.x > 0) {
            if (direction.y >= 0) {
                cubeCoord.x += distance;
            } else {
                cubeCoord.x += distance;
                cubeCoord.z -= distance;
            }
        } else if (direction.x < 0) {
            if (direction.y >= 0) {
                cubeCoord.x -= distance;
                cubeCoord.z += distance;
            } else {
                cubeCoord.x -= distance;
            }
        } else {
            if (direction.y > 0) {
                cubeCoord.z += distance;
            } else if (direction.y < 0) {
                cubeCoord.z -= distance;
            }
        }
        return coordCube2Offset(cubeCoord.x, cubeCoord.y, cubeCoord.z);
    }

    public FloatPair getDirectionVector(Pair origin, Pair destination) {
        return this.getDirectionVector(origin.x, origin.y, destination.x, destination.y);
    }

    public FloatPair getDirectionVector(int originx, int originy, int destinationx, int destinationy) {
        final FloatPair cell2 = this.world2window(destinationx, destinationy);
        final FloatPair cell1 = this.world2window(originx, originy);
        return new FloatPair(cell2.x - cell1.x, cell2.y - cell1.y);
    }

    public PositionArray getCircularRange(Pair origin, int minRange, int maxRange) {
        return this.getCircularRange(origin.x, origin.y, minRange, maxRange);
    }

    public PositionArray getCircularRange(int x, int y, int minRange, int maxRange) {
        final PositionArray unvisited = new PositionArray(gameMap);
        final PositionArray visited = new PositionArray(gameMap);
        final Pair start = new Pair(x, y);
        unvisited.add(start);
        while (unvisited.size > 0) {
            final Pair current = unvisited.pop();
            for (final Pair neighbor : this.getNeighbors(current.x, current.y)) {
                final int distance = this.getDistance(x, y, neighbor.x, neighbor.y);
                if (distance <= maxRange) {
                    if (!visited.contains(neighbor, false)) {
                        visited.add(neighbor);
                        if (!unvisited.contains(neighbor, false)) {
                            unvisited.add(neighbor);
                        }
                    }
                }
            }
        }
        return this.pruneBelowMinRange(visited, new Pair(x, y), minRange);
    }

    public PositionArray getLinearRange(Pair origin, Pair destination) {
        return this.getLinearRange(origin.x, origin.y, destination.x, destination.y);
    }

    public PositionArray getLinearRange(int x, int y, int x0, int y0) {
        final PositionArray highlights = new PositionArray(gameMap);
        highlights.add(x, y);
        final Vec3 cubeCoordsOrigin = MapTools.coordOffset2Cube(x, y);
        final Vec3 cubeCoordsDestination = MapTools.coordOffset2Cube(x0, y0);
        final int dx = cubeCoordsOrigin.x - cubeCoordsDestination.x;
        final int dy = cubeCoordsOrigin.y - cubeCoordsDestination.y;
        final int dz = cubeCoordsOrigin.z - cubeCoordsDestination.z;
        float distance = Math.max(Math.abs(dx - dy), Math.abs(dy - dz));
        distance = Math.max(distance, Math.abs(dz - dx));
        if (distance > 0) {
            for (float i = 0; i <= distance; i++) {
                final float currentX = (cubeCoordsOrigin.x * (i / distance))
                        + (cubeCoordsDestination.x * (1 - (i / distance)));
                final float currentY = (cubeCoordsOrigin.y * (i / distance))
                        + (cubeCoordsDestination.y * (1 - (i / distance)));
                final float currentZ = (cubeCoordsOrigin.z * (i / distance))
                        + (cubeCoordsDestination.z * (1 - (i / distance)));
                final Vec3 currentRoundUp = new Vec3(roundCubeCoord(currentX + 0.05f,
                        currentY + 0.07f, currentZ));
                final Vec3 currentRoundDown = new Vec3(roundCubeCoord(currentX - 0.05f,
                        currentY - 0.07f, currentZ));
                Pair offsetCoord = new Pair(MapTools.coordCube2Offset(currentRoundUp.x,
                        currentRoundUp.y, currentRoundUp.z));
                if (offsetCoord.y >= 0) {
                    highlights.add(offsetCoord.x, offsetCoord.y);
                }
                if (!currentRoundUp.equals(currentRoundDown)) {
                    offsetCoord = MapTools.coordCube2Offset(currentRoundDown.x, currentRoundDown.y,
                            currentRoundDown.z);
                    if (offsetCoord.y >= 0) {
                        highlights.add(offsetCoord.x, offsetCoord.y);
                    }
                }
            }
        }
        return highlights;
    }

    public PositionArray getWaveRange(int originx, int originy, int targetx, int targety) {
        final FloatPair direction = this.getDirectionVector(originx, originy, targetx, targety);
        return this.getWaveRange(new Pair(targetx, targety), direction);
    }

    public PositionArray getWaveRange(Pair origin, Pair destination) {
        final FloatPair direction = this.getDirectionVector(origin.x, origin.y, destination.x,
                destination.y);
        return this.getWaveRange(new Pair(destination.x, destination.y), direction);
    }

    public PositionArray getWaveRange(Pair origin, FloatPair direction) {
        final PositionArray highlights = new PositionArray(gameMap);
        highlights.add(origin);
        highlights.addAll(this.getArcAdjacents(origin, direction));
        return highlights;
    }

    public PositionArray getArcRange(int originx, int originy, int targetx, int targety,
            int minRange, int maxRange) {
        return this.getArcRange(new Pair(targetx, targety),
                this.getDirectionVector(originx, originy, targetx, targety), minRange, maxRange);
    }

    public PositionArray getArcRange(Pair origin, FloatPair direction, int minRange, int maxRange) {
        if (maxRange > gameMap.width) {
            maxRange = gameMap.width;
        }
        // Hack to make it compatible with wave
        direction.x = -direction.x;
        direction.y = -direction.y;
        final PositionArray open = new PositionArray(gameMap);
        final PositionArray closed = new PositionArray(gameMap);
        closed.add(origin);
        while (closed.size > 0) {
            final Pair position = closed.removeIndex(0);
            if (!open.contains(position, false)
                    && (this.getDistance(origin.x, origin.y, position.x, position.y) <= maxRange)) {
                closed.addAll(this.getArcAdjacents(position, direction));
                open.add(position);
            }
        }
        return this.pruneBelowMinRange(open, origin, minRange);
    }

    private PositionArray getArcAdjacents(Pair target, FloatPair direction) {
        final PositionArray highlights = new PositionArray(gameMap);
        int offset = 0;
        if ((target.x % 2) == 0) {
            offset = 1;
        }
        if (direction.x > 0) {
            if (direction.y >= 0) {
                highlights.add(target.x - 1, (target.y + 1) - offset);
                highlights.add(target.x, target.y - 1);
            } else {
                highlights.add(target.x, target.y + 1);
                highlights.add(target.x - 1, target.y - offset);
            }
        } else if (direction.x < 0) {
            if (direction.y >= 0) {
                highlights.add(target.x + 1, (target.y + 1) - offset);
                highlights.add(target.x, target.y - 1);
            } else {
                highlights.add(target.x + 1, target.y - offset);
                highlights.add(target.x, target.y + 1);
            }
        } else {
            if (direction.y > 0) {
                highlights.add(target.x + 1, target.y - offset);
                highlights.add(target.x - 1, target.y - offset);
            } else if (direction.y < 0) {
                highlights.add(target.x + 1, (target.y + 1) - offset);
                highlights.add(target.x - 1, (target.y + 1) - offset);
            }
        }
        return highlights;
    }

    public PositionArray getFlowerRange(Pair origin, int minRange, int maxRange) {
        return this.getFlowerRange(origin.x, origin.y, minRange, maxRange);
    }

    public PositionArray getFlowerRange(int x, int y, int minRange, int maxRange) {
        if (maxRange > gameMap.width) {
            maxRange = gameMap.width;
        }
        final PositionArray highlights = new PositionArray(gameMap);
        highlights.add(x, y);
        if (maxRange > 0) {
            int movey = 0;
            for (int currentRange = 1; currentRange <= maxRange; currentRange++) {
                highlights.add(x, y + currentRange);
                if ((currentRange % 2) == 0) {
                    highlights.add(x + currentRange, (y - currentRange) + movey);
                    highlights.add(x - currentRange, (y - currentRange) + movey);
                } else {
                    if ((x % 2) == 1) {
                        highlights.add(x + currentRange, y - movey);
                        highlights.add(x - currentRange, y - movey);
                    } else {
                        highlights.add(x + currentRange, y - movey - 1);
                        highlights.add(x - currentRange, y - movey - 1);
                    }
                    movey++;
                }
            }
        }
        return this.pruneBelowMinRange(highlights, new Pair(x, y), minRange);
    }

    public PositionArray getReverseFlowerRange(Pair origin, int minRange, int maxRange) {
        return this.getReverseFlowerRange(origin.x, origin.y, minRange, maxRange);
    }

    public PositionArray getReverseFlowerRange(int x, int y, int minRange, int maxRange) {
        if (maxRange > gameMap.width) {
            maxRange = gameMap.width;
        }
        final PositionArray highlights = new PositionArray(gameMap);
        highlights.add(x, y);
        if (maxRange > 0) {
            int movey = 0;
            for (int currentRange = 1; currentRange <= maxRange; currentRange++) {
                highlights.add(x, y - currentRange);
                if ((currentRange % 2) == 0) {
                    highlights.add(x - currentRange, (y + movey));
                    highlights.add(x + currentRange, (y + movey));
                } else {
                    if ((x % 2) == 0) {
                        highlights.add(x - currentRange, (y + movey));
                        highlights.add(x + currentRange, (y + movey));
                    } else {
                        highlights.add(x - currentRange, (y + movey) + 1);
                        highlights.add(x + currentRange, (y + movey) + 1);
                    }
                    movey++;
                }
            }
        }
        return this.pruneBelowMinRange(highlights, new Pair(x, y), minRange);
    }

    private PositionArray pruneBelowMinRange(PositionArray open, Pair origin, int minRange) {
        final PositionArray highlights = new PositionArray(gameMap);
        for (final Pair cell : open) {
            final int distance = this.getDistance(origin.x, origin.y, cell.x, cell.y);
            if (distance >= minRange) {
                highlights.add(cell);
            }
        }
        return highlights;
    }

    public static Vec3 coordOffset2Cube(int x, int y) {
        final Vec3 coord = new Vec3();
        coord.x = x;
        coord.z = y - ((x - (x % 2)) / 2);
        coord.y = -x - coord.z;
        return coord;
    }

    public static Pair coordCube2Offset(int x, int y, int z) {
        final Pair coord = new Pair();
        coord.x = x;
        coord.y = (z + ((x - (x % 2)) / 2));
        return coord;
    }

    public static int[] roundCubeCoord(double x, double y, double z) {
        float rx = Math.round(x);
        float ry = Math.round(y);
        float rz = Math.round(z);
        final int s = (int)(rx + ry + rz);
        if (s != 0) {
            final float x_err = (float)Math.abs(rx - x);
            final float y_err = (float)Math.abs(ry - y);
            final float z_err = (float)Math.abs(rz - z);
            if ((x_err >= y_err) && (x_err >= z_err)) {
                rx -= s;
                if (x_err == y_err) {
                    ry -= s;
                }
                if (x_err == z_err) {
                    rz -= s;
                }
            } else if (y_err > z_err) {
                ry -= s;
                if (y_err == z_err) {
                    rz -= s;
                }
            } else {
                rz -= s;
            }
        }
        return new int[] {
                (int)rx, (int)ry, (int)rz
        };
    }
}
