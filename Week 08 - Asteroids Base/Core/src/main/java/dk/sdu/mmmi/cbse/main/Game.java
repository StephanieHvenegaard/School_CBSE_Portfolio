package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.astroid.AstroidControlSystem;
import dk.sdu.mmmi.cbse.astroid.AstroidPlugin;
import dk.sdu.mmmi.cbse.astroid.Splitter;
import dk.sdu.mmmi.cbse.colision.Collider;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColorPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
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

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

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

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        IPostEntityProcessingService collistion = new Collider();
        postProssors.add(collistion);
        IEntityProcessingService splitter = new Splitter();
        entityProcessors.add(splitter);
        
        
        IGamePluginService playerPlugin = new PlayerPlugin();
        IGamePluginService enemyPlugin = new EnemyPlugin();
        IGamePluginService astroidPlugin = new AstroidPlugin();
        IGamePluginService projectilePlugin = new ProjectilePlugin();
        
        PlayerControlSystem playerProcess = new PlayerControlSystem();
                playerProcess.setBulletService(new ProjectileServieProvider());        
        EnemyControlSystem enemyProcess = new EnemyControlSystem();
                enemyProcess.setBulletService(new ProjectileServieProvider());
        IEntityProcessingService astroidProcess = new AstroidControlSystem();
        IEntityProcessingService projectileProcess = new ProjectileControlSystem();
        
        
        entityPlugins.add(playerPlugin);
        entityPlugins.add(enemyPlugin);
        entityPlugins.add(astroidPlugin);
        entityPlugins.add(projectilePlugin);
        
        entityProcessors.add(playerProcess);
        entityProcessors.add(enemyProcess);
        entityProcessors.add(astroidProcess);
        entityProcessors.add(projectileProcess);
        
        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : entityPlugins) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }
        // post updater
        for (IPostEntityProcessingService entityProcessorService : postProssors) {
            entityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
                ColorPart color = entity.getPart(ColorPart.class);
           if(color != null)
           {
               int[] colors = color.getColor();
               sr.setColor(colors[0],colors[1],colors[2], 1);
           }
           else            
           {
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
}
