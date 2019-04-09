/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.endgame;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.commonentities.Asteroid;
import dk.sdu.mmmi.cbse.commonentities.Enemy;
import dk.sdu.mmmi.cbse.commonentities.Player;
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
