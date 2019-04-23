/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.asteroid.asteriod;

import dk.se.asteroid.commonentities.Asteroid;
import dk.se.asteroid.commonentities.AsteroidType;
import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import dk.se.common.data.World;
import dk.se.common.data.entityparts.MovingPart;
import dk.se.common.data.entityparts.PositionPart;
import dk.se.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Random;

/**
 *
 * @author Stephanie
 */


public class AstroidControlSystem implements IEntityProcessingService {

    int numPoints = 6;
    Random rnd = new Random(10);
    float angle = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            float speed = (float) Math.random() * 10f + 40f;
            if (rnd.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setLeft(true);
            }

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            updateShape(asteroid);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity asteroid) {

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        float[] shapex = new float[6];
        float[] shapey = new float[6];
        Asteroid asAsteroid = (Asteroid) asteroid;
//        SplitterPart splitter = asAsteroid.getPart(SplitterPart.class);
        if (asAsteroid.getSize() == AsteroidType.LARGE.getSize()) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) cos(angle + radians) * asAsteroid.getSize();
                shapey[i] = y + (float) sin(angle + radians) * asAsteroid.getSize();
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if (asAsteroid.getSize() == AsteroidType.MEDIUM.getSize()) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) cos(angle + radians) * asAsteroid.getSize();
                shapey[i] = y + (float) sin(angle + radians) * asAsteroid.getSize();
                angle += 2 * 3.1415f / numPoints;
            }
        }
        if (asAsteroid.getSize() == AsteroidType.SMALL.getSize()) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) cos(angle + radians) * asAsteroid.getSize();
                shapey[i] = y + (float) sin(angle + radians) * asAsteroid.getSize();
                angle += 2 * 3.1415f / numPoints;
            }
        }

        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }
}

//        implements IEntityProcessingService {
//
//    Random r = new Random(System.currentTimeMillis()); // dot wanna create this for every proces call
//        boolean UP = false;
//        boolean LEFT = false;
//        boolean RIGHT = false;
//
//    @Override
//    public void process(GameData gameData, World world) {
//
//         UP = ((r.nextInt()*100)<5);
//         LEFT = ((r.nextInt()*100)<75);
//         RIGHT = ((r.nextInt()*100)<5);
//
//        for (Entity enemy : world.getEntities(Asteroid.class)) {
//            PositionPart positionPart = enemy.getPart(PositionPart.class);
//            MovingPart movingPart = enemy.getPart(MovingPart.class);
//
//            movingPart.setLeft(LEFT);
//            movingPart.setRight(RIGHT);
//            movingPart.setUp(UP);
//
//            movingPart.process(gameData, enemy);
//            positionPart.process(gameData, enemy);
//
//            updateShape(enemy);
//        }
//    }
// 
//    private void updateShape(Entity entity) {
//        float[] shapex = entity.getShapeX();
//        float[] shapey = entity.getShapeY();
//        PositionPart positionPart = entity.getPart(PositionPart.class);
//        float x = positionPart.getX();
//        float y = positionPart.getY();
//        float radians = positionPart.getRadians();
//        
//       
//
//        shapex[0] = (float) (x + Math.cos(radians + 0 * 3.1415f / 5) * entity.getSize());
//        shapey[0] = (float) (y + Math.sin(radians + 0 * 3.1415f / 5) * entity.getSize());
//
//        shapex[1] = (float) (x + Math.cos(radians + 2 * 3.1415f / 5) * entity.getSize());
//        shapey[1] = (float) (y + Math.sin(radians + 2 * 3.1415f / 5) * entity.getSize());
//        
//        shapex[2] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getSize());
//        shapey[2] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getSize());
//
//        shapex[3] = (float) (x + Math.cos(radians + 6 * 3.1415f / 5) * entity.getSize());
//        shapey[3] = (float) (y + Math.sin(radians + 6 * 3.1415f / 5) * entity.getSize());
//
//        shapex[4] = (float) (x + Math.cos(radians + 8 * 3.1415f / 5) * entity.getSize());
//        shapey[4] = (float) (y + Math.sin(radians + 8 * 3.1415f / 5) * entity.getSize());
//
//        entity.setShapeX(shapex);
//        entity.setShapeY(shapey);
//    }
//    
//}
