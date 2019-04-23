/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.se.asteroid.projectile;

import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import dk.se.common.data.entityparts.ColorPart;
import dk.se.common.data.entityparts.LifePart;
import dk.se.common.data.entityparts.MovingPart;
import dk.se.common.data.entityparts.PositionPart;
import dk.se.common.data.entityparts.TimerPart;
import dk.se.common.services.ProjectileSPI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 *
 * @author Stephanie
 */
public class ProjectileServieProvider implements ProjectileSPI {

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        ColorPart color = shooter.getPart(ColorPart.class);
        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadians();
        float speed = 350;

        Entity projectile = new Projectile();
        projectile.setRadius(2);

        float bx = (float) cos(radians) * shooter.getRadius() * projectile.getRadius();
        float by = (float) sin(radians) * shooter.getRadius() * projectile.getRadius();

        projectile.add(new PositionPart(bx + x, by + y, radians));
        projectile.add(new MovingPart(0, 5000000, speed, 5));
        projectile.add(new TimerPart(1.5f));
        projectile.add(new LifePart(1,3f));
        
        if (color != null) {
            projectile.add(color);
        } else {
            projectile.add(new ColorPart());
        }

        projectile.setShapeX(new float[2]);
        projectile.setShapeY(new float[2]);

        return projectile;
    }

}
