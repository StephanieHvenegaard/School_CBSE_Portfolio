package dk.se.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.ENTER;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import dk.se.common.data.World;
import dk.se.common.data.entityparts.ColorPart;
import dk.se.common.services.IEntityProcessingService;
import dk.se.common.services.IGamePluginService;
import dk.se.common.services.IPostEntityProcessingService;
import dk.se.core.managers.GameInputProcessor;
import java.util.ArrayList;
import java.util.List;



public class Game implements ApplicationListener {

    private SpriteBatch batch;
    private BitmapFont font;
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private String playerWonString = "You Won";
    private String platerLostString = "You Lost";
    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private List<IPostEntityProcessingService> postProssors = new ArrayList<>();

    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        init();
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        update();
        draw();
        gameData.getKeys().update(); // moved here to make more sence.
    }

    private void update() {        
        if (!gameData.isGameOwer()) {

            // Update
            for (IEntityProcessingService entityProcessorService : entityProcessors) {
                entityProcessorService.process(gameData, world);
            }
            // post updater
            for (IPostEntityProcessingService entityProcessorService : postProssors) {
                entityProcessorService.process(gameData, world);
            }
        }
        if (gameData.getKeys().isPressed(ENTER)) {
            reset();
        }
    }

    private void draw() {

        if (gameData.isGameOwer()) {

            batch.begin();
            if(gameData.isPlayerWon())
                font.draw(batch, playerWonString, gameData.getDisplayWidth()/2, gameData.getDisplayHeight()/2);
            else
                font.draw(batch, platerLostString, gameData.getDisplayWidth()/2, gameData.getDisplayHeight()/2);
            batch.end();

        } else {
            for (Entity entity : world.getEntities()) {
                ColorPart color = entity.getPart(ColorPart.class);
                if (color != null) {
                    int[] colors = color.getColor();
                    sr.setColor(colors[0], colors[1], colors[2], 1);
                } else {
                    sr.setColor(1, 1, 1, 1);
                }

                sr.begin(ShapeRenderer.ShapeType.Line);

                float[] shapex = entity.getShapeX();
                float[] shapey = entity.getShapeY();

                for (int i = 0, j = shapex.length - 1;
                        i < shapex.length;
                        j = i++) {

                    sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                }

                sr.end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private void reset() {
        Entity[] array = world.getEntities().toArray(new Entity[world.getEntities().size()]);
        for (Entity e : array) {
            world.removeEntity(e);
        }
        gameData.setGameOwer(false);
        gameData.setPlayerWon(false);

        entityProcessors.clear();
        entityPlugins.clear();
        postProssors.clear();
        init();

    }

    private void init() {
//       <-- initialize -->

//        IPostEntityProcessingService collistion = new Collider();
//        postProssors.add(collistion);
//
//        IPostEntityProcessingService endgame = new EndGame();
//        postProssors.add(endgame);
//
//        IEntityProcessingService splitter = new Splitter();
//        entityProcessors.add(splitter);
//
//        IGamePluginService playerPlugin = new PlayerPlugin();
//        IGamePluginService enemyPlugin = new EnemyPlugin();
//        IGamePluginService astroidPlugin = new AstroidPlugin();
//        IGamePluginService projectilePlugin = new ProjectilePlugin();
//
//        PlayerControlSystem playerProcess = new PlayerControlSystem();
//        playerProcess.setBulletService(new ProjectileServieProvider());
//
//        EnemyControlSystem enemyProcess = new EnemyControlSystem();
//        enemyProcess.setBulletService(new ProjectileServieProvider());
//
//        IEntityProcessingService astroidProcess = new AstroidControlSystem();
//        IEntityProcessingService projectileProcess = new ProjectileControlSystem();
//
//        entityPlugins.add(playerPlugin);
//        entityPlugins.add(enemyPlugin);
//        entityPlugins.add(astroidPlugin);
//        entityPlugins.add(projectilePlugin);
//
//        entityProcessors.add(playerProcess);
//        entityProcessors.add(enemyProcess);
//        entityProcessors.add(astroidProcess);
//        entityProcessors.add(projectileProcess);
//
//        // Lookup all Game Plugins using ServiceLoader
//        for (IGamePluginService iGamePlugin : entityPlugins) {
//            iGamePlugin.start(gameData, world);
//        }
    }
}
