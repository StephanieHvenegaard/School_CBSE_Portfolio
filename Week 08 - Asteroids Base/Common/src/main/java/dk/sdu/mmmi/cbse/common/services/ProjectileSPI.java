package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
/**
 * @author Steph
 */
public interface ProjectileSPI {
    Entity createBullet(Entity e, GameData gameData);
}