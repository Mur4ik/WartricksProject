
package com.wartricks.lifecycle;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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
import com.wartricks.utils.Constants.Players;
import com.wartricks.utils.EntityFactory;

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
        versusGame = new VersusGame(gameMap, gameWorld, camera);
        movementSystem = gameWorld.setSystem(new MovementSystem(), true);
        spriteRenderSystem = gameWorld.setSystem(new SpriteRenderSystem(camera, spriteBatch,
                gameMap), true);
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
        // TODO creating players
        EntityFactory.createPlayer(world, gameMap, Players.ONE, 10);
        EntityFactory.createPlayer(world, gameMap, Players.TWO, 10);
        // TODO creating skills
        // EntityFactory.createSkill(gameWorld, "move", 2, 1, 2, 700, 0);
        // EntityFactory.createSkill(gameWorld, "attack", 2, 1, 1, 600, 2);
        // EntityFactory.createSkill(gameWorld, "jump", 2, 1, 1, 500, 1);
        // EntityFactory.createSkill(gameWorld, "impactrueno", 2, 1, 1, 400, 2);
        // EntityFactory.createSkill(gameWorld, "gorro del amor", 2, 1, 1, 300, 1);
        // EntityFactory.createSkill(gameWorld, "piruloNOjutsu", 2, 1, 1, 200, 0);
        // TODO creating creatures
        final Array<String> characters = new Array<String>(new String[] {
            "apple"
        });
        final Array<Integer> creatures = versusGame.api.loadCreatures(characters);
        versusGame.api.assignCreatureToPlayer(creatures.first(), Players.ONE, 0, 0);
        // EntityFactory.createCreature(world, gameMap, "dash", Players.ONE,
        // new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 5,
        // 3, 100, 5, new Array<String>(new String[] {
        // "piruloNOjutsu", "move"
        // }));
        // EntityFactory.createCreature(world, gameMap, "kirby", Players.TWO,
        // new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 9,
        // 4, 100, 5, new Array<String>(new String[] {
        // "jump", "move"
        // }));
        // EntityFactory.createCreature(world, gameMap, "apple", Players.ONE,
        // new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 0,
        // 1, 100, 5, new Array<String>(new String[] {
        // "move", "impactrueno"
        // }));
        // EntityFactory.createCreature(world, gameMap, "pinkie", Players.ONE,
        // new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 3,
        // 6, 100, 5, new Array<String>(new String[] {
        // "move", "gorro del amor", "error"
        // }));
        // EntityFactory.createCreature(world, gameMap, "kirby", Players.TWO,
        // new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 9,
        // 5, 100, 5, new Array<String>(new String[] {
        // "move", "impactrueno", "gorro del amor", "piruloNOjutsu"
        // }));
        versusGame.startLogic();
    }

    @Override
    public void render(final float delta) {
        super.render(delta);
        // fpsLogger.log();
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
