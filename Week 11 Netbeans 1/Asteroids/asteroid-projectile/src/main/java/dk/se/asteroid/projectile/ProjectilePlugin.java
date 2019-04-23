/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.asteroid.projectile;

import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import dk.se.common.data.World;
import dk.se.common.services.IGamePluginService;

/**
 *
 * @author Stephanie
 */
public class ProjectilePlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {        
        System.out.println("projectiles working");
    }



    @Override
    public void stop(GameData gameData, World world) {
       for (Entity projectile : world.getEntities(Projectile.class)) {
           world.removeEntity(projectile);
       }        
    }
}
