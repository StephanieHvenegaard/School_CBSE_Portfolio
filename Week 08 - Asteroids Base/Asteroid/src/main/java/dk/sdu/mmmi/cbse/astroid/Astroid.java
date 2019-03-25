/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.astroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

/**
 *
 * @author Stephanie
 */
public class Astroid extends Entity
        implements IEntityProcessingService {

    Random r = new Random(System.currentTimeMillis()); // dot wanna create this for every proces call
    boolean UP = false;
    boolean LEFT = false;
    boolean RIGHT = false;

    @Override
    public void process(GameData gameData, World world) {

        UP = ((r.nextInt() * 100) < 75);
        LEFT = ((r.nextInt() * 100) < 75);
        RIGHT = ((r.nextInt() * 100) < 75);

        for (Entity astroid : world.getEntities(Astroid.class)) {
            PositionPart positionPart = astroid.getPart(PositionPart.class);
            MovingPart movingPart = astroid.getPart(MovingPart.class);

            movingPart.setLeft(LEFT);
            movingPart.setRight(RIGHT);
            movingPart.setUp(UP);

            movingPart.process(gameData, astroid);
            positionPart.process(gameData, astroid);           
        }
    }

}
