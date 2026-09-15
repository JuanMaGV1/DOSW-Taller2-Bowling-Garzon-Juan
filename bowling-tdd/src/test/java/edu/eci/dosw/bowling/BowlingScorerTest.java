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

    // ------------------- B2 ------------------------
    @Test
    @DisplayName("Juego sin strikes ni spares suma todos los pinos")
    void noStrikesNoSpares_sumsAllPins() {
        // 10 frames de 3+4 = 7 => total 70
        for (int i = 0; i < 10; i++) {
            game.roll(3);
            game.roll(4);
        }
        assertEquals(70, game.score());
    }

    // ------------------- B3 ------------------------
    @Test
    @DisplayName("Spare en frame 1 + siguiente tiro 3 => frame 1 = 13")
    void spareInFirstFrame_addsNextRoll() {
        game.roll(5);
        game.roll(5);   // spare
        game.roll(3);
        // resto de frames a 0
        rollMany(17, 0);
        assertEquals(16, game.score()); // 13 + 3 = 16
    }
}