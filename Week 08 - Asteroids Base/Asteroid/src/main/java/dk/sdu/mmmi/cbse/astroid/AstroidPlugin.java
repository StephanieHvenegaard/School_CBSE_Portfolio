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
        astroid = createAss(gameData);
        world.addEntity(astroid);
    }

    private Entity createAss(GameData gameData) {
        Random r = new Random();
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 100;
        float rotationSpeed = 2;
        float x = r.nextInt(gameData.getDisplayWidth());
        float y = r.nextInt(gameData.getDisplayHeight());
        float radians = 3.1415f / 2;
        
        Entity a = new Astroid();
        a.setSize(20.0f);
        a.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        a.add(new PositionPart(x, y, radians));
        a.add(new ColorPart(0,0,1));
        
        return a;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(astroid);
    }

}
