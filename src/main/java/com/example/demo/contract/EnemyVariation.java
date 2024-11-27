package com.example.demo.contract;

import com.example.demo.actor.EnemyPlane;

public interface EnemyVariation {
    EnemyPlane makeEnemyPlane(double initialXPos, double initialYPos, int type);
}
