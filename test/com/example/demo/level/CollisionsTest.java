package com.example.demo.level;

import com.example.demo.actor.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import static org.mockito.Mockito.*;

class CollisionsTest {
    private List<ActiveActorDestructible> userProjectiles;
    private List<ActiveActorDestructible> enemyProjectiles;
    private List<ActiveActorDestructible> enemyUnits;
    private List<ActiveActorDestructible> friendlyUnits;
    private List<ActiveActorDestructible> healthPoints;
    @BeforeEach
    void setup() {
        userProjectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        enemyUnits = new ArrayList<>();
        friendlyUnits = new ArrayList<>();
        healthPoints = new ArrayList<>();
    }
    @Test
    void testUserProjectileCollisions(){
        UserProjectile userProjectile = mock(UserProjectile.class);
        userProjectiles.add(userProjectile);

        EnemyPlane enemyPlane = mock(EnemyPlane.class);
        enemyUnits.add(enemyPlane);

        Bounds userBounds = mock(Bounds.class);
        Bounds enemyBounds = mock(Bounds.class);

        when(userBounds.intersects(any(Bounds.class))).thenReturn(true);
        when(enemyBounds.intersects(any(Bounds.class))).thenReturn(true);

        when(userProjectile.getBoundsInParent()).thenReturn(userBounds);
        when(enemyPlane.getBoundsInParent()).thenReturn(enemyBounds);

        CollisionHandler.handleUserProjectileCollisions(userProjectiles, enemyUnits);

        verify(userProjectile, times(1)).takeDamage();
        verify(enemyPlane, times(1)).takeDamage();
    }

    @Test
    void testEnemyProjectileCollisions(){
        EnemyProjectile enemyProjectile = mock(EnemyProjectile.class);
        enemyProjectiles.add(enemyProjectile);

        UserPlane friendlyUnit = mock(UserPlane.class);
        friendlyUnits.add(friendlyUnit);

        Bounds enemyBounds = mock(Bounds.class);
        Bounds friendlyBounds = mock(Bounds.class);

        when(enemyBounds.intersects(any(Bounds.class))).thenReturn(true);
        when(friendlyBounds.intersects(any(Bounds.class))).thenReturn(true);

        when(enemyProjectile.getBoundsInParent()).thenReturn(enemyBounds);
        when(friendlyUnit.getBoundsInParent()).thenReturn(friendlyBounds);

        CollisionHandler.handleEnemyProjectileCollisions(enemyProjectiles, friendlyUnits);

        verify(enemyProjectile, times(1)).takeDamage();
        verify(friendlyUnit, times(1)).takeDamage();
    }

    @Test
    void testPlaneCollisions(){
        UserPlane friendlyUnit = mock(UserPlane.class);
        friendlyUnits.add(friendlyUnit);

        EnemyPlane enemyPlane = mock(EnemyPlane.class);
        enemyUnits.add(enemyPlane);

        Bounds friendlyBounds = mock(Bounds.class);
        Bounds enemyBounds = mock(Bounds.class);

        when(friendlyBounds.intersects(any(Bounds.class))).thenReturn(true);
        when(enemyBounds.intersects(any(Bounds.class))).thenReturn(true);

        when(friendlyUnit.getBoundsInParent()).thenReturn(friendlyBounds);
        when(enemyPlane.getBoundsInParent()).thenReturn(enemyBounds);

        CollisionHandler.handlePlaneCollisions(friendlyUnits, enemyUnits);

        verify(friendlyUnit, times(1)).takeDamage();
        verify(enemyPlane, times(1)).destroy();
    }

    @Test
    void testUserHealthPointCollisionsWhenMaxHP(){
        int playerInitHealth = 5;
        UserPlane friendlyUnit = mock(UserPlane.class);

        HealthPoint healthPoint = mock(HealthPoint.class);
        healthPoints.add(healthPoint);

        Bounds friendlyBounds = mock(Bounds.class);
        Bounds healthPointBounds = mock(Bounds.class);

        when(friendlyBounds.intersects(any(Bounds.class))).thenReturn(true);
        when(healthPointBounds.intersects(any(Bounds.class))).thenReturn(true);

        when(friendlyUnit.getBoundsInParent()).thenReturn(friendlyBounds);
        when(friendlyUnit.getHealth()).thenReturn(playerInitHealth);
        when(healthPoint.getBoundsInParent()).thenReturn(healthPointBounds);

        CollisionHandler.handleUserHealthPointCollisions(
                playerInitHealth,
                friendlyUnit,
                healthPoints
        );

        verify(friendlyUnit, times(0)).repairDamage();
        verify(healthPoint, times(1)).destroy();
    }

    @Test
    void testUserHealthPointCollisionsWhenDamaged(){
        int playerInitHealth = 5;
        UserPlane friendlyUnit = mock(UserPlane.class);

        HealthPoint healthPoint = mock(HealthPoint.class);
        healthPoints.add(healthPoint);

        Bounds friendlyBounds = mock(Bounds.class);
        Bounds healthPointBounds = mock(Bounds.class);

        when(friendlyBounds.intersects(any(Bounds.class))).thenReturn(true);
        when(healthPointBounds.intersects(any(Bounds.class))).thenReturn(true);

        when(friendlyUnit.getBoundsInParent()).thenReturn(friendlyBounds);
        when(friendlyUnit.getHealth()).thenReturn(playerInitHealth-1);
        when(healthPoint.getBoundsInParent()).thenReturn(healthPointBounds);

        CollisionHandler.handleUserHealthPointCollisions(
                playerInitHealth,
                friendlyUnit,
                healthPoints
        );

        verify(friendlyUnit, times(1)).repairDamage();
        verify(healthPoint, times(1)).destroy();
    }
}