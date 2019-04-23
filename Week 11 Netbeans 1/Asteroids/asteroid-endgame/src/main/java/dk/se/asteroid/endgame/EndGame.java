/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.asteroid.endgame;

import dk.se.asteroid.commonentities.Asteroid;
import dk.se.asteroid.commonentities.Enemy;
import dk.se.asteroid.commonentities.Player;
import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import dk.se.common.data.World;
import dk.se.common.services.IPostEntityProcessingService;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Stephanie
 */
public class EndGame implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        Entity p = null;
        Entity e = null;
        List<Asteroid> a = new ArrayList<>();
        for (Entity entity : world.getEntities(Player.class)) {
            p = entity;
        }
        if (p == null) {
            gameData.setGameOwer(true);
            gameData.setPlayerWon(false);
            return;
        }

        for (Entity entity : world.getEntities(Enemy.class)) {
            e = entity;
        }
        if (e == null) {
            gameData.setGameOwer(true);
            gameData.setPlayerWon(true);
        }
    }
}
