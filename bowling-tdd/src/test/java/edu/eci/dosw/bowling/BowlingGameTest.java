package edu.eci.dosw.bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BowlingGameTest {

    private BowlingGame game;

    @BeforeEach 
    void setUp() {
        game = new BowlingGame();
    }

    // ================================================
    // MODULO A - roll() validaciones y estado
    // ================================================

    // ------------------- A1 ------------------------

    // ------------------- A2 ------------------------
    @Test
    @DisplayName("A2: roll(-1) lanza IllegalArgumentException")
    void rollNegativePins_throwsException() {
        // Arrange
        BowlingGame game = new BowlingGame();
        // Act & Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(-1)
        );
    }
}