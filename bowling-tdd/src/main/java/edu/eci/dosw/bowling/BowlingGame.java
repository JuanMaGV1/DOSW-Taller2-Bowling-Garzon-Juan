package edu.eci.dosw.bowling; 

import java.util.ArrayList; 
import java.util.List; 
  
/** 
 * Motor de un juego de Bowling para un jugador. 
 * Un juego tiene exactamente 10 frames. 
 */ 
public class BowlingGame { 
    private final List<Frame> frames; 
    private int currentFrame; 

    private static final int MIN_PINS = 0;
    private static final int MAX_PINS = 10;
    
    public BowlingGame() { 
        this.frames = new ArrayList<>(); 
        this.currentFrame = 0; 
        this.frames.add(new Frame(0));
    } 

    private void validatePins(int pins){
        if (pins < MIN_PINS || pins > MAX_PINS){
            throw new IllegalArgumentException("Pines fuera de rango [0, 10]: " + pins);
        }
    }
    /** 
     * Registra pinos derribados. Lanza IllegalArgumentException si pines < 0 o > 10. 
     *  Lanza IllegalStateException si el juego ya termino. 
     */ 
    public void roll(int pins) { 
        // TODO: implementar con TDD (RED -> GREEN -> REFACTOR) 
        validatePins(pins);
        frames.get(0).addRoll(pins);
    } 

    /** Puntaje total. Lanza IllegalStateException si el juego no esta completo. */ 
    public int score() { 
        // TODO: implementar con TDD 
        return 0; 
    } 
    
    /** true cuando los 10 frames han sido completados. */ 
    public boolean isComplete() { 
        // TODO: implementar con TDD 
        return false; 
    } 
    
    public List<Frame> getFrames() { return List.copyOf(frames); } 

}