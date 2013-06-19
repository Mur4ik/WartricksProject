
package com.wartricks.lifecycle;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.wartricks.logic.GameMap;
import com.wartricks.logic.VersusGame;
import com.wartricks.systems.EnergyRenderSystem;
import com.wartricks.systems.HudRenderSystem;
import com.wartricks.systems.MapHighlightRenderSystem;
import com.wartricks.systems.MapRenderSystem;
import com.wartricks.systems.MovementSystem;
import com.wartricks.systems.PathRenderSystem;
import com.wartricks.systems.SkillRenderSystem;
import com.wartricks.systems.SpriteRenderSystem;
import com.wartricks.utils.Constants;

public class BoardScene extends AbstractScreen {
    private final StringBuilder output = new StringBuilder();

    private OrthographicCamera hudCamera;

    private World gameWorld;

    private SpriteRenderSystem spriteRenderSystem;

    private FPSLogger fpsLogger;

    private HudRenderSystem hudRenderSystem;

    private GameMap gameMap;

    private MapRenderSystem mapRenderSystem;

    private PathRenderSystem pathRenderSystem;

    private SpriteBatch spriteBatch;

    private MovementSystem movementSystem;

    private MapHighlightRenderSystem mapHighlightRenderSystem;

    private EnergyRenderSystem energyRenderSystem;

    private SkillRenderSystem skillRenderSystem;

    private VersusGame versusGame;

    public BoardScene(final BoardGame game, World world, SpriteBatch batch) {
        super(game, world);
        // TODO Lua server, shouldn't be here
        // this.createLuaState();
        // serverThread = new ServerThread();
        // serverThread.start();
        spriteBatch = batch;
        gameMap = new GameMap(Constants.HEX_ROW_SIZE, Constants.HEX_COL_SIZE,
                Constants.HEX_MAP_WIDTH, Constants.HEX_MAP_HEIGHT, camera);
        fpsLogger = new FPSLogger();
        gameWorld = world;
        hudCamera = new OrthographicCamera();
        versusGame = new VersusGame(gameMap, gameWorld, camera, stage);
        movementSystem = gameWorld.setSystem(new MovementSystem(), true);
        spriteRenderSystem = gameWorld.setSystem(new SpriteRenderSystem(camera, spriteBatch,
                versusGame), true);
        mapRenderSystem = gameWorld.setSystem(new MapRenderSystem(camera, gameMap, spriteBatch),
                true);
        pathRenderSystem = gameWorld.setSystem(new PathRenderSystem(camera, spriteBatch, gameMap),
                true);
        mapHighlightRenderSystem = gameWorld.setSystem(new MapHighlightRenderSystem(camera,
                gameMap, spriteBatch), true);
        hudRenderSystem = gameWorld.setSystem(new HudRenderSystem(hudCamera, spriteBatch,
                versusGame), true);
        energyRenderSystem = gameWorld.setSystem(new EnergyRenderSystem(hudCamera, spriteBatch),
                true);
        skillRenderSystem = gameWorld.setSystem(new SkillRenderSystem(hudCamera, spriteBatch,
                versusGame), true);
        gameWorld.initialize();
        versusGame.startLogic();
    }

    @Override
    public void render(final float delta) {
        // fpsLogger.log();
        super.render(delta);
        spriteBatch.setProjectionMatrix(camera.combined);
        movementSystem.process();
        mapRenderSystem.process();
        mapHighlightRenderSystem.process();
        spriteRenderSystem.process();
        pathRenderSystem.process();
        energyRenderSystem.process();
        skillRenderSystem.process();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hudRenderSystem.process();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        game.world.deleteSystem(hudRenderSystem);
        game.world.deleteSystem(mapRenderSystem);
        game.world.deleteSystem(pathRenderSystem);
        game.world.deleteSystem(spriteRenderSystem);
    }

    @Override
    public void resize(final int width, final int height) {
        super.resize(width, height);
        camera.zoom = 0.6f;
        final Vector3 origin = new Vector3(-200, -50, 0);
        camera.translate(origin);
        hudCamera.setToOrtho(false, width, height);
        Gdx.app.debug(this.toString(), "resize");
    }

    @Override
    public void pause() {
        Gdx.app.debug(this.toString(), "pause");
    }

    @Override
    public void resume() {
        Gdx.app.debug(this.toString(), "resume");
    }

    @Override
    public void show() {
        Gdx.app.debug(this.toString(), "show");
    }

    @Override
    public void hide() {
        Gdx.app.debug(this.toString(), "hide");
    }
}
