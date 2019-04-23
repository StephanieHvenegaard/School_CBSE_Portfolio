/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.asteroid.asteriod;

import dk.se.asteroid.commonentities.Asteroid;
import dk.se.asteroid.commonentities.AsteroidType;
import static dk.se.asteroid.commonentities.AsteroidType.MEDIUM;
import static dk.se.asteroid.commonentities.AsteroidType.SMALL;
import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import dk.se.common.data.World;
import dk.se.common.data.entityparts.LifePart;
import dk.se.common.data.entityparts.MovingPart;
import dk.se.common.data.entityparts.PositionPart;
import dk.se.common.data.entityparts.SplitterPart;
import dk.se.common.services.IEntityProcessingService;
import java.util.Random;


/**
 *
 * @author Stephanie
 */
public class Splitter implements IEntityProcessingService {

    Random rnd = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            Asteroid theAsteroid = (Asteroid) asteroid;
            PositionPart posPart = asteroid.getPart(PositionPart.class);
            SplitterPart splitter = asteroid.getPart(SplitterPart.class);

            if (theAsteroid.getType() == AsteroidType.LARGE && splitter.ShouldSplit()) {
                splitter.setShouldSplit(false);
                Asteroid mediumAsteroidOne = createMediumAsteroid(posPart.getX(), posPart.getY());
                Asteroid mediumAsteroidTwo = createMediumAsteroid(posPart.getX(), posPart.getY());
                world.addEntity(mediumAsteroidOne);
                world.addEntity(mediumAsteroidTwo);
                world.removeEntity(theAsteroid);
            }

            if (theAsteroid.getSize() == AsteroidType.MEDIUM.getSize() && splitter.ShouldSplit()) {
                splitter.setShouldSplit(false);
                Asteroid smallAsteroidOne = createSmallAsteroid(posPart.getX(), posPart.getY());
                Asteroid smallAsteroidTwo = createSmallAsteroid(posPart.getX(), posPart.getY());
                world.addEntity(smallAsteroidOne);
                world.addEntity(smallAsteroidTwo);
                world.removeEntity(theAsteroid);
            }
        }
    }

    private Asteroid createSmallAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 13f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(SMALL);
        asteroid.add(Asteroid.getAstroidColor());
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + rnd.nextInt(50), y + rnd.nextInt(50), radians));
        asteroid.add(new LifePart(2, 69));        
        asteroid.setRadius(5);

        return (Asteroid) asteroid;
    }

    private Asteroid createMediumAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(MEDIUM);

        asteroid.add(Asteroid.getAstroidColor());
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + rnd.nextInt(50), y + rnd.nextInt(50), radians));
        asteroid.add(new LifePart(4, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(10);

        return (Asteroid) asteroid;
    }
}
