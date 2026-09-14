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
    @Test 
    @DisplayName("roll(0) registra 0 pines sin excepcion")
    void rollZeroPins_registersZero(){
        assertDoesNotThrow(() -> game.roll(0));
        assertEquals(1, game.getFrames().get(0).getRolls().size());
        assertEquals(0, game.getFrames().get(0).getRolls().get(0));
    }
    
    // ------------------- A2 ------------------------
    @Test
    @DisplayName("A2: roll(-1) lanza IllegalArgumentException")
    void rollNegativePins_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> game.roll(-1));
    }
}