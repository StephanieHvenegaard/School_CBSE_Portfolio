package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.astroid.AstroidControlSystem;
import dk.sdu.mmmi.cbse.astroid.AstroidPlugin;
import dk.sdu.mmmi.cbse.astroid.Splitter;
import dk.sdu.mmmi.cbse.colision.Collider;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.ENTER;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColorPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.ServiceLoader;
import dk.sdu.mmmi.cbse.endgame.EndGame;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.projectile.ProjectileControlSystem;
import dk.sdu.mmmi.cbse.projectile.ProjectilePlugin;
import dk.sdu.mmmi.cbse.projectile.ProjectileServieProvider;
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
            // Service loader                
            ServiceLoader.UpdatePlugins(gameData, world);
            ServiceLoader.UpdatePostPlugins(gameData, world);
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
        ServiceLoader.LookupPlugins(gameData, world);

    }
}
