package com.example.demo.level;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import com.example.demo.actor.*;
import com.example.demo.util.GameScoreCalculator;

public class GameScoreCalculatorTest {
    @Test
    void testHigherXHigherScore(){
        UserPlane userPlane1 = mock(UserPlane.class);
        UserPlane userPlane2 = mock(UserPlane.class);

        double lowerBound = 600;
        double upperBound = 0;
        when(userPlane1.getXLowerBound()).thenReturn(lowerBound);
        when(userPlane1.getXUpperBound()).thenReturn(upperBound);
        when(userPlane2.getXLowerBound()).thenReturn(lowerBound);
        when(userPlane2.getXUpperBound()).thenReturn(upperBound);

        when(userPlane1.getTranslateX()).thenReturn(0.0);
        when(userPlane2.getTranslateX()).thenReturn(500.0);

        int score1 = GameScoreCalculator.calculateUserScoreByPosition(userPlane1);
        int score2 = GameScoreCalculator.calculateUserScoreByPosition(userPlane2);

        assertTrue(score2 > score1,
                "UserPlane at higher X position should have a higher score than one at a lower X position.");
    }
}
