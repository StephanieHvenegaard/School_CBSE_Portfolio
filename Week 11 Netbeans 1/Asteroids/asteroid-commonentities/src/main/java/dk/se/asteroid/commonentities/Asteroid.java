/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.asteroid.commonentities;

import dk.se.common.data.Entity;
import dk.se.common.data.entityparts.ColorPart;

/**
 *
 * @author Stephanie
 */
public class Asteroid extends Entity {

    private AsteroidType type;

    public static ColorPart getAstroidColor() {
        // got tired of changing it in five documents
        return new ColorPart(0, 0, 1);
    }

    public Asteroid(AsteroidType type) {
        this.type = type;
        this.setSize(type.getSize());
    }

    @Override
    public float getSize() {
        return type.getSize();
    }

    public AsteroidType getType() {
        return type;
    }
}


//        extends Entity
//        implements IEntityProcessingService {
//
//    Random r = new Random(System.currentTimeMillis()); // dot wanna create this for every proces call
//    boolean UP = false;
//    boolean LEFT = false;
//    boolean RIGHT = false;
//
//    @Override
//    public void process(GameData gameData, World world) {
//
//        UP = ((r.nextInt() * 100) < 75);
//        LEFT = ((r.nextInt() * 100) < 75);
//        RIGHT = ((r.nextInt() * 100) < 75);
//
//        for (Entity astroid : world.getEntities(Asteroid.class)) {
//            PositionPart positionPart = astroid.getPart(PositionPart.class);
//            MovingPart movingPart = astroid.getPart(MovingPart.class);
//
//            movingPart.setLeft(LEFT);
//            movingPart.setRight(RIGHT);
//            movingPart.setUp(UP);
//
//            movingPart.process(gameData, astroid);
//            positionPart.process(gameData, astroid);           
//        }
//    }
