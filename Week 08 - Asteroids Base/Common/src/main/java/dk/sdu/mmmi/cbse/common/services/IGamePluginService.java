package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {
    /**
     * when called add the component game
     * @param gameData Game data duh.
     * @param world world data duh.
     */
    void start(GameData gameData, World world);
    /**
     * when called removes the component from the game.
     * @param gameData Game data duh.
     * @param world world data duh.
     */
    void stop(GameData gameData, World world);
}
