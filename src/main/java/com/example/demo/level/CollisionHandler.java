package com.example.demo.level;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.UserPlane;

import java.util.List;

public class CollisionHandler {
    CollisionHandler() {}

    public static void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits)
    {
        for (ActiveActorDestructible userProjectile : userProjectiles) {
            for (ActiveActorDestructible enemyUnit : enemyUnits) {
                if (userProjectile.getBoundsInParent().intersects(enemyUnit.getBoundsInParent())) {
                    userProjectile.takeDamage();
                    enemyUnit.takeDamage();
                }
            }
        }
    }

    public static void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits)
    {
        for (ActiveActorDestructible enemyProjectile : enemyProjectiles) {
            for (ActiveActorDestructible friendlyUnit : friendlyUnits) {
                if (enemyProjectile.getBoundsInParent().intersects(friendlyUnit.getBoundsInParent())) {
                    enemyProjectile.takeDamage();
                    friendlyUnit.takeDamage();
                }
            }
        }
    }

    public static void handlePlaneCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits)
    {
        for (ActiveActorDestructible friendlyUnit : friendlyUnits) {
            for (ActiveActorDestructible enemyUnit : enemyUnits) {
                if (friendlyUnit.getBoundsInParent().intersects(enemyUnit.getBoundsInParent())) {
                    friendlyUnit.takeDamage();
                    enemyUnit.destroy();
                }
            }
        }
    }

    public static void handleUserHealthPointCollisions(
            int playerInitHealth,
            UserPlane userPlane,
            List<ActiveActorDestructible> healthPoints)
    {
        for (ActiveActorDestructible healthPoint : healthPoints) {
            if (userPlane.getBoundsInParent().intersects(healthPoint.getBoundsInParent())) {
                if(userPlane.getHealth() < playerInitHealth){
                    userPlane.repairDamage();
                }
                healthPoint.destroy();
            }
        }
    }
}
