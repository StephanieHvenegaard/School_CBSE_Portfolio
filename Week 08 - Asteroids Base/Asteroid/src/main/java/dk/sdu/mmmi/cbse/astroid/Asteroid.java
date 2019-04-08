/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColorPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

/**
 *
 * @author Stephanie
 */
public class Asteroid extends Entity {

    private AsteroidType type;

    public static ColorPart getAstroidColor()
    {
        // got tired of changing it in five documents
        return new ColorPart(0, 0, 1);
    }
    
    public Asteroid(AsteroidType type) {
        this.type = type;
    }

    @Override
    public float getSize() {
        return type.getSize();
    }
}

enum AsteroidType {
    LARGE(20f),
    MEDIUM(10f),
    SMALL(5f);

    private float size;

    private AsteroidType(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
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
