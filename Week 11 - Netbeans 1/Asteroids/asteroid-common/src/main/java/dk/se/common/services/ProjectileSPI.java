package dk.se.common.services;

import dk.se.common.data.Entity;
import dk.se.common.data.GameData;

/**
 * @author Steph
 */
public interface ProjectileSPI {
    Entity createBullet(Entity e, GameData gameData);
}