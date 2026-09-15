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

    // ------------------- A3 ------------------------
    @Test 
    @DisplayName("roll (11) lanza IllegalArgumenException")
    void rollPinsGreaterThan10_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> game.roll(11));
    }

    // ------------------- A4 ------------------------
    @Test
    @DisplayName("roll(7)+roll(6) lanza IllegalArgumentException en el segundo tiro")
    void twoRollsSumGreaterThan10_throwsException() {
        game.roll(7);
        assertThrows(IllegalArgumentException.class, () -> game.roll(6));
    }

    // ------------------- A5 ------------------------

    // ------------------- A6 ------------------------
    @Test
    @DisplayName("roll(10) en frame normal marca STRIKE y avanza al siguiente frame")
    void roll10_marksStrikeAndAdvancesFrame() {
            game.roll(10);

            Frame first = game.getFrames().get(0);
            assertTrue(first.isStrike());
            assertEquals(FrameType.STRIKE, first.getType());
            assertEquals(2, game.getFrames().size()); // se creó el frame 2
        }

    // ------------------- A7 ------------------------
    @Test
    @DisplayName("roll(5)+roll(5) marca SPARE")
        void roll5And5_marksSpare() {
            BowlingGame game = new BowlingGame();
            game.roll(5);
            game.roll(5);
            assertTrue(game.getFrames().get(0).isSpare());
            assertEquals(FrameType.SPARE, game.getFrames().get(0).getType());
    }
}