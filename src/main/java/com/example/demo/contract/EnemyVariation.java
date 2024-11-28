package com.example.demo.contract;

import com.example.demo.actor.EnemyPlane;

/**
 * Represents a contract for creating different variations of enemy planes.
 */
public interface EnemyVariation {

    /**
     * Creates an instance of an {@link EnemyPlane} based on the given type and initial positions.
     *
     * @param initialXPos the initial X position of the enemy plane.
     * @param initialYPos the initial Y position of the enemy plane.
     * @param type the type of enemy plane to create.
     * @return an instance of {@link EnemyPlane} corresponding to the specified type.
     */
    EnemyPlane makeEnemyPlane(double initialXPos, double initialYPos, int type);

}