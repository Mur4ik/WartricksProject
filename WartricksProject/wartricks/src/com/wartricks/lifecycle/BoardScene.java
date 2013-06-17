
package com.wartricks.lifecycle;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import com.wartricks.utils.PlatformUtils;

public class BoardScene extends AbstractScreen {
    private final static int LISTEN_PORT = 3333;

    public ServerThread serverThread;

    public LuaState L;

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
        // TODO creating skills
        EntityFactory.createSkill(gameWorld, "move", 2, 1, 2, 700, 5).addToWorld();
        EntityFactory.createSkill(gameWorld, "attack", 2, 1, 1, 600, 5).addToWorld();
        EntityFactory.createSkill(gameWorld, "jump", 2, 1, 1, 500, 5).addToWorld();
        EntityFactory.createSkill(gameWorld, "impactrueno", 2, 1, 1, 400, 5).addToWorld();
        EntityFactory.createSkill(gameWorld, "gorro del amor", 2, 1, 1, 300, 5).addToWorld();
        EntityFactory.createSkill(gameWorld, "piruloNOjutsu", 2, 1, 1, 200, 5).addToWorld();
        // TODO creating creatures
        EntityFactory.createCreature(world, gameMap, "dash", Players.ONE,
                new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 5,
                3, 100, 5, new Array<String>(new String[] {
                        "move", "piruloNOjutsu"
                })).addToWorld();
        EntityFactory.createCreature(world, gameMap, "kirby", Players.TWO,
                new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 9,
                4, 100, 5, new Array<String>(new String[] {})).addToWorld();
        EntityFactory.createCreature(world, gameMap, "apple", Players.ONE,
                new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 0,
                1, 100, 5, new Array<String>(new String[] {
                        "move", "impactrueno"
                })).addToWorld();
        EntityFactory.createCreature(world, gameMap, "pinkie", Players.ONE,
                new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 3,
                6, 100, 5, new Array<String>(new String[] {
                        "move", "gorro del amor", "error"
                })).addToWorld();
        EntityFactory.createCreature(world, gameMap, "kirby", Players.TWO,
                new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1f), 9,
                5, 100, 5, new Array<String>(new String[] {
                        "move", "impactrueno", "gorro del amor", "piruloNOjutsu"
                })).addToWorld();
        EntityFactory.createPlayer(world, gameMap, Players.ONE, 10).addToWorld();
        EntityFactory.createPlayer(world, gameMap, Players.TWO, 10).addToWorld();
        versusGame.startLogic();
        // final LoadScript script = new LoadScript("init.lua");
        // script.runScriptFunction("wave", null);
        // final LoadScript playerScript = new LoadScript("characters/player.lua");
        // playerScript.runScriptFunction("create", EntityFactory.class, world);
        // EntityFactory.createLabel(world, "life", "100", 50, 50).addToWorld();
        // EntityFactory.createLabel(world, "score", "0", 850, 50).addToWorld();
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

    private class ServerThread extends Thread {
        public boolean stopped;

        @Override
        public void run() {
            stopped = false;
            try {
                final ServerSocket server = new ServerSocket(LISTEN_PORT);
                this.show("Server started on port " + LISTEN_PORT);
                while (!stopped) {
                    final Socket client = server.accept();
                    final BufferedReader in = new BufferedReader(new InputStreamReader(
                            client.getInputStream()));
                    final PrintWriter out = new PrintWriter(client.getOutputStream());
                    String line = null;
                    while (!stopped && ((line = in.readLine()) != null)) {
                        final String s = line.replace('\001', '\n');
                        if (s.startsWith("--mod:")) {
                            final int i1 = s.indexOf(':'), i2 = s.indexOf('\n');
                            final String mod = s.substring(i1 + 1, i2);
                            final String file = Gdx.files.getLocalStoragePath() + "/"
                                    + mod.replace('.', '/') + ".lua";
                            final FileWriter fw = new FileWriter(file);
                            fw.write(s);
                            fw.close();
                            // package.loaded[mod] = nil
                            L.getGlobal("package");
                            L.getField(-1, "loaded");
                            L.pushNil();
                            L.setField(-2, mod);
                            out.println("wrote " + file + "\n");
                            out.flush();
                        } else {
                            System.out.println(s);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println(s);
                                    String res = BoardScene.this.safeEvalLua(s);
                                    res = res.replace('\n', '\001');
                                    out.println(res);
                                    out.flush();
                                }
                            });
                        }
                    }
                }
                server.close();
            } catch (final Exception e) {
                this.show(e.toString());
            }
        }

        private void show(final String s) {
            Gdx.app.log("Wartickss", s);
        }
    }

    String safeEvalLua(String src) {
        String res = null;
        try {
            res = this.evalLua(src);
        } catch (final LuaException e) {
            res = e.getMessage() + "\n";
        }
        return res;
    }

    String evalLua(String src) throws LuaException {
        L.setTop(0);
        int ok = L.LloadString(src);
        if (ok == 0) {
            L.getGlobal("debug");
            L.getField(-1, "traceback");
            L.remove(-2);
            L.insert(-2);
            ok = L.pcall(0, 0, -2);
            if (ok == 0) {
                final String res = output.toString();
                output.setLength(0);
                return res;
            }
        }
        throw new LuaException(this.errorReason(ok) + ": " + L.toString(-1));
        // return null;
    }

    private String errorReason(int error) {
        switch (error) {
            case 4:
                return "Out of memory";
            case 3:
                return "Syntax error";
            case 2:
                return "Runtime error";
            case 1:
                return "Yield error";
        }
        return "Unknown error " + error;
    }

    private void createLuaState() {
        L = LuaStateFactory.newLuaState();
        L.openLibs();
        try {
            L.pushJavaObject(this);
            L.setGlobal("activity");
            final JavaFunction print = new JavaFunction(L) {
                @Override
                public int execute() throws LuaException {
                    for (int i = 2; i <= L.getTop(); i++) {
                        final int type = L.type(i);
                        final String stype = L.typeName(type);
                        String val = null;
                        if (stype.equals("userdata")) {
                            final Object obj = L.toJavaObject(i);
                            if (obj != null) {
                                val = obj.toString();
                            }
                        } else if (stype.equals("boolean")) {
                            val = L.toBoolean(i) ? "true" : "false";
                        } else {
                            val = L.toString(i);
                        }
                        if (val == null) {
                            val = stype;
                        }
                        output.append(val);
                        output.append("\t");
                    }
                    output.append("\n");
                    return 0;
                }
            };
            print.register("print");
            final JavaFunction assetLoader = new JavaFunction(L) {
                @Override
                public int execute() throws LuaException {
                    final String name = L.toString(-1);
                    try {
                        final FileHandle handle = Gdx.files.internal(PlatformUtils.getPath(name
                                + ".lua"));
                        final String file = handle.readString();
                        L.LdoString(file);
                        return 1;
                    } catch (final Exception e) {
                        final ByteArrayOutputStream os = new ByteArrayOutputStream();
                        e.printStackTrace(new PrintStream(os));
                        L.pushString("Cannot load module " + name + ":\n" + os.toString());
                        return 1;
                    }
                }
            };
            L.getGlobal("package"); // package
            L.getField(-1, "loaders"); // package loaders
            final int nLoaders = L.objLen(-1); // package loaders
            L.pushJavaFunction(assetLoader); // package loaders loader
            L.rawSetI(-2, nLoaders + 1); // package loaders
            L.pop(1); // package
            L.getField(-1, "path"); // package path
            final String customPath = Gdx.files.getLocalStoragePath() + "/?.lua";
            L.pushString(";" + customPath); // package path custom
            L.concat(2); // package pathCustom
            L.setField(-2, "path"); // package
            L.pop(1);
        } catch (final Exception e) {
        }
    }
}
