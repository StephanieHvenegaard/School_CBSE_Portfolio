/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColorPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author Stephanie
 */
public class ProjectileControlSystem implements IEntityProcessingService {
   
    

    
    @Override
    public void process(GameData gameData, World world) {
        // Create Projectiles
        
        // Update Current projectiles
        for (Entity projectile : world.getEntities(Projectile.class)) {
            PositionPart positionPart = projectile.getPart(PositionPart.class);
            MovingPart movingPart = projectile.getPart(MovingPart.class);

            movingPart.setUp(true);

            movingPart.process(gameData, projectile);
            positionPart.process(gameData, projectile);

            updateShape(projectile);
        }
        // Remove Projectiles.
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 2);
        shapey[0] = (float) (y + Math.sin(radians) * 2);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 2);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 2);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 1.75);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 1.75);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 2);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 2);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private Entity createProjectile(World world) {
        
        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity projectile = new Projectile();
        projectile.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        projectile.add(new PositionPart(x, y, radians));
        projectile.add(new ColorPart(0, 1, 0));

        return projectile;
    }
}
