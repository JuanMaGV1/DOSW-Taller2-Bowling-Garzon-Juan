package edu.eci.dosw.bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BowlingScorerTest {
    private BowlingGame game;

    @BeforeEach
    void setUp() {
        game = new BowlingGame();
    }

    private void rollMany(int times, int pins) {
        for (int i = 0; i < times; i++) game.roll(pins);
    }

    // ================================================
    // MODULO B - calculate()
    // ================================================

    // ------------------- B1 ------------------------
    @Test
    @DisplayName("Juego con todos los tiros a 0 da score 0")
    void allZeros_scoreZero() {
        rollMany(20, 0);
        assertEquals(0, game.score());
    }
}