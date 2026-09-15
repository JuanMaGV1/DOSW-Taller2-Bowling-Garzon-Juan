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
    @Test
    @DisplayName("roll() tras completar el juego lanza IllegalStateException")
    void rollAfterGameComplete_throwsIllegalState() {
        // 10 frames de 0,2
        for (int i = 0; i < 10; i++) {
            game.roll(0);
            game.roll(2);
        }
        assertTrue(game.isComplete());
        assertThrows(IllegalStateException.class, () -> game.roll(3));
    }
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
            game.roll(5);
            game.roll(5);
            assertTrue(game.getFrames().get(0).isSpare());
            assertEquals(FrameType.SPARE, game.getFrames().get(0).getType());
    }

    // ------------------- A8 -------------------------
    @Test
    @DisplayName("Frame 10 con strike acepta 3 tiros sin excepción")
    void tenthFrameWithStrike_allowsThreeRolls() {
        // 9 frames con 0,2
        for (int i = 0; i < 9; i++) {
            game.roll(0);
            game.roll(2);
        }
        // Frame 10: strike + 2 bonus
        game.roll(10);
        game.roll(5);
        game.roll(3);

        assertEquals(3, game.getFrames().get(9).getRolls().size());
    }
}