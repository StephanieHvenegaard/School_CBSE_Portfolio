/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.colision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Stephanie
 */
public class Collider implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                // get life parts on all entities
                LifePart entityLife = entity.getPart(LifePart.class);
                LifePart collisionLife = collisionDetection.getPart(LifePart.class);

                // if one or both components dont have a life component skip it. 
                if (entityLife == null || collisionLife == null) {
                    continue;
                }
                // if the two entities are identical, skip the iteration
                if (entity.getID().equals(collisionDetection.getID())) {
                    continue;
                }
                // remove entities with zero in expiration
                if (entityLife.getExpiration() <= 0) {
                    world.removeEntity(entity);
                    // if collisioner expiration is zero or beloq, remove.
                    if (collisionLife.getExpiration() <= 0) {
                        world.removeEntity(collisionDetection);
                    }
                }

                // CollisionDetection
                if (this.Collides(entity, collisionDetection)) {
                    // if entity has been hit, and should have its life reduced
                    int life = entityLife.getLife() ;
                    if (life> 0) {
                        entityLife.setLife(life  - 1);
                        // if entity is out of life - remove
                        if (entityLife.getLife() <= 0) {
                            SplitterPart split = entity.getPart(SplitterPart.class);
                            if (split == null) {
                                world.removeEntity(entity);
                            }
                            else 
                                split.setShouldSplit(true);
                        }
                    }
                }
            }
        }
    }

    public Boolean Collides(Entity entity, Entity entity2) {
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov.getX() - (float) entMov2.getX();
        float dy = (float) entMov.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < (entity.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }

}
