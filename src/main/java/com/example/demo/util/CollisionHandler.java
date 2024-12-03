package com.example.demo.util;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.Boss;
import com.example.demo.actor.UserPlane;

import java.util.List;

/**
 * Utility class for handling various collision scenarios in the game.
 */
public class CollisionHandler {

    /**
     * Private constructor to prevent instantiation.
     */
    private CollisionHandler() {}

    /**
     * Handles collisions between user projectiles and enemy units.
     *
     * @param userPlane       the user's plane.
     * @param userProjectiles the list of user projectiles.
     * @param enemyUnits      the list of enemy units.
     * @return the score increment based on successful hits.
     */
    public static int handleUserProjectileCollisions(
            UserPlane userPlane,
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyUnits
    ) {
        int scoreIndex = 0;

        for (ActiveActorDestructible userProjectile : userProjectiles) {
            for (ActiveActorDestructible enemyUnit : enemyUnits) {
                if (userProjectile.getBoundsInParent().intersects(enemyUnit.getBoundsInParent())) {
                    userProjectile.takeDamage();
                    enemyUnit.takeDamage();
                    scoreIndex += GameScoreCalculator.calculateUserScoreByPosition(userPlane);
                }
            }
        }

        return scoreIndex;
    }

    /**
     * Handles collisions between user projectiles and a boss unit.
     *
     * @param userPlane       the user's plane.
     * @param userProjectiles the list of user projectiles.
     * @param boss            the boss unit.
     * @return the score increment based on successful hits.
     */
    public static int handleUserProjectileBossCollisions(
            UserPlane userPlane,
            List<ActiveActorDestructible> userProjectiles,
            Boss boss
    ) {
        int scoreIndex = 0;

        for (ActiveActorDestructible userProjectile : userProjectiles) {
            if (userProjectile.getBoundsInParent().intersects(boss.getBoundsInParent())) {
                userProjectile.takeDamage();

                if (!boss.getIsShielded()) {
                    boss.takeDamage();
                    scoreIndex += GameScoreCalculator.calculateUserScoreByPosition(userPlane);
                }
            }
        }

        return scoreIndex;
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     *
     * @param enemyProjectiles the list of enemy projectiles.
     * @param friendlyUnits    the list of friendly units.
     */
    public static void handleEnemyProjectileCollisions(
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> friendlyUnits
    ) {
        for (ActiveActorDestructible enemyProjectile : enemyProjectiles) {
            for (ActiveActorDestructible friendlyUnit : friendlyUnits) {
                if (enemyProjectile.getBoundsInParent().intersects(friendlyUnit.getBoundsInParent())) {
                    enemyProjectile.takeDamage();
                    friendlyUnit.takeDamage();
                }
            }
        }
    }

    /**
     * Handles collisions between friendly planes and enemy planes.
     *
     * @param friendlyUnits the list of friendly units.
     * @param enemyUnits    the list of enemy units.
     */
    public static void handlePlaneCollisions(
            List<ActiveActorDestructible> friendlyUnits,
            List<ActiveActorDestructible> enemyUnits
    ) {
        for (ActiveActorDestructible friendlyUnit : friendlyUnits) {
            for (ActiveActorDestructible enemyUnit : enemyUnits) {
                if (friendlyUnit.getBoundsInParent().intersects(enemyUnit.getBoundsInParent())) {
                    friendlyUnit.takeDamage();
                    enemyUnit.destroy();
                }
            }
        }
    }

    /**
     * Handles collisions between the user's plane and health points.
     *
     * @param playerInitHealth the initial health of the player.
     * @param userPlane        the user's plane.
     * @param healthPoints     the list of health points.
     */
    public static void handleUserHealthPointCollisions(
            int playerInitHealth,
            UserPlane userPlane,
            List<? extends ActiveActorDestructible> healthPoints
    ) {
        for (ActiveActorDestructible healthPoint : healthPoints) {
            if (userPlane.getBoundsInParent().intersects(healthPoint.getBoundsInParent())) {
                if (userPlane.getHealth() < playerInitHealth) {
                    userPlane.repairDamage();
                }
                healthPoint.destroy();
            }
        }
    }

    /**
     * Handles collisions between the user's plane and boss fire deactivators.
     *
     * @param userPlane        the user's plane.
     * @param boss             the boss unit whose fire needs to be deactivated.
     * @param fireDeactivators the list of fire deactivator objects.
     *
     * If a collision is detected between the user's plane and any fire deactivator:
     * <ul>
     *     <li>The boss's fire is deactivated.</li>
     *     <li>All fire deactivators involved in the collision are destroyed.</li>
     * </ul>
     */
    public static void handleUserBossFireDeactivatorCollisions(
            UserPlane userPlane,
            Boss boss,
            List<? extends ActiveActorDestructible> fireDeactivators
    ) {
        boolean collided = false;

        for (ActiveActorDestructible fireDeactivator : fireDeactivators) {
            if (userPlane.getBoundsInParent().intersects(fireDeactivator.getBoundsInParent())) {
                collided = true;
                boss.deactivateFire();
            }
        }

        if(collided){
            for (ActiveActorDestructible fireDeactivator : fireDeactivators) {
                fireDeactivator.destroy();
            }
        }
    }
}