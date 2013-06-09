
package com.wartricks.systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.wartricks.boards.GameMap;
import com.wartricks.custom.FloatPair;
import com.wartricks.custom.Pair;
import com.wartricks.custom.PositionArray;
import com.wartricks.utils.PlatformUtils;

public class MapHighlightRenderSystem extends VoidEntitySystem {
    private SpriteBatch spriteBatch;

    private Texture highlight;

    private OrthographicCamera camera;

    private GameMap gameMap;

    private float t;

    public MapHighlightRenderSystem(OrthographicCamera camera, GameMap map, SpriteBatch batch) {
        this.camera = camera;
        gameMap = map;
        spriteBatch = batch;
    }

    @Override
    protected void initialize() {
        highlight = new Texture(Gdx.files.internal(PlatformUtils
                .getPath("textures/maptiles/hex_blank.png")));
        t = 0;
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    @Override
    protected void processSystem() {
        final PositionArray highlighted = gameMap.highlighted;
        if ((highlighted == null) || (highlighted.size < 1)) {
            return;
        }
        // Get bottom left and top right coordinates of camera viewport and convert
        // into grid coordinates for the map
        final int x0 = MathUtils.floor(camera.frustum.planePoints[0].x / gameMap.colSize) - 1;
        final int y0 = MathUtils.floor(camera.frustum.planePoints[0].y / gameMap.rowSize) - 1;
        final int x1 = MathUtils.floor(camera.frustum.planePoints[2].x / gameMap.colSize) + 2;
        final int y1 = MathUtils.floor(camera.frustum.planePoints[2].y / gameMap.rowSize) + 1;
        spriteBatch.setColor(255, 0, 0, (0.5f / 8) * (7 + MathUtils.cos(8 * t)));
        for (final Pair cell : highlighted) {
            if ((cell.x < x0) || (cell.x > x1) || (cell.y < y0) || (cell.y > y1)) {
                continue;
            }
            final FloatPair coords = gameMap.tools.world2window(cell.x, cell.y);
            spriteBatch.draw(highlight, coords.x - (highlight.getWidth() / 2), coords.y
                    - (highlight.getHeight() / 2));
        }
        t += Gdx.graphics.getDeltaTime();
    }

    @Override
    protected void begin() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }
}
