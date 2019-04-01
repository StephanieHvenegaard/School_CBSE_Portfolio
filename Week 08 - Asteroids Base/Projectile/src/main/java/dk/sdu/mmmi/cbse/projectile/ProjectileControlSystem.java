/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColorPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author Stephanie
 */
public class ProjectileControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // Update Current projectiles
        for (Entity projectile : world.getEntities(Projectile.class)) {
            TimerPart timerPart = projectile.getPart(TimerPart.class);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(projectile);
            } else {
                timerPart.process(gameData, projectile);

                PositionPart positionPart = projectile.getPart(PositionPart.class);
                MovingPart movingPart = projectile.getPart(MovingPart.class);

                movingPart.setUp(true);

                movingPart.process(gameData, projectile);
                positionPart.process(gameData, projectile);

                updateShape(projectile);
            }
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
