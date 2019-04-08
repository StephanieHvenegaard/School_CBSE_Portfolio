/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astroid;

import static dk.sdu.mmmi.cbse.astroid.AsteroidType.LARGE;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColorPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

/**
 *
 * @author Stephanie
 */
public class AstroidPlugin implements IGamePluginService {

    private Entity astroid;


    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        astroid = createLargeAsteroid(gameData);
        world.addEntity(astroid);
    }


    private Asteroid createLargeAsteroid(GameData gameData){
       float speed = (float) Math.random() * 10f + 40f;
       float radians = 3.1415f / 2 + (float) Math.random();
        float x = gameData.getDisplayWidth() / 2 + 100;
        float y = gameData.getDisplayHeight() / 2 + 50;
        Entity asteroid = new Asteroid(LARGE);

        //asteroid.setColor(new float[]{255f, 0f, 160f, 1f});
        asteroid.add(Asteroid.getAstroidColor());
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(6, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(15);

        return (Asteroid) asteroid;  
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(astroid);
    }

}
