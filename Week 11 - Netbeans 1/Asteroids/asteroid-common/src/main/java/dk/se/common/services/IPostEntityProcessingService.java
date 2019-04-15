package dk.se.common.services;

import dk.se.common.data.GameData;
import dk.se.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    /**
     * post process calls if any.
     * @param gameData
     * @param world
     */
    void process(GameData gameData, World world);
}
