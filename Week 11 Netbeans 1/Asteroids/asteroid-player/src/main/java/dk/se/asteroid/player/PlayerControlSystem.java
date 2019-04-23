package dk.se.asteroid.player;

import dk.se.asteroid.commonentities.Player;
import dk.se.common.data.Entity;
import dk.se.common.data.GameData;
import static dk.se.common.data.GameKeys.LEFT;
import static dk.se.common.data.GameKeys.RIGHT;
import static dk.se.common.data.GameKeys.SPACE;
import static dk.se.common.data.GameKeys.UP;
import dk.se.common.data.World;
import dk.se.common.data.entityparts.MovingPart;
import dk.se.common.data.entityparts.PositionPart;
import dk.se.common.services.IEntityProcessingService;
import dk.se.common.services.ProjectileSPI;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerControlSystem implements IEntityProcessingService {

    private ProjectileSPI ProjectileService;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            
            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));

            if (gameData.getKeys().isDown(SPACE)) {
                Entity bullet = ProjectileService.createBullet(player, gameData);
                world.addEntity(bullet);
            };

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    //TODO: Dependency injection via Declarative Services

    public void setBulletService(ProjectileSPI service) {
        this.ProjectileService = service;
    }

    public void removeBulletService() {
        this.ProjectileService = null;
    }
}
