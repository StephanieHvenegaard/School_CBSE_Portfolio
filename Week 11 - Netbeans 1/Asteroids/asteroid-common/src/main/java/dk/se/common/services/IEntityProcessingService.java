package dk.se.common.services;

import dk.se.common.data.GameData;
import dk.se.common.data.World;



public interface IEntityProcessingService {
/**
 * Prosses the entity
 * @param gameData
 * @param world 
 */
    void process(GameData gameData, World world);
}
