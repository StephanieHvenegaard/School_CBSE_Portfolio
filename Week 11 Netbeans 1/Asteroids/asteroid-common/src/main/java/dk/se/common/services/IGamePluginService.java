package dk.se.common.services;

import dk.se.common.data.GameData;
import dk.se.common.data.World;



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
