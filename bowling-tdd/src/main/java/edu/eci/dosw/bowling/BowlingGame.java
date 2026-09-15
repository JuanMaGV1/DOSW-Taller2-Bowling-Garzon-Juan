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
    private static final int MAX_FRAMES = 10;
    
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

    private Frame currentFrame() {
        return frames.get(frames.size() - 1);
    }

    private void validateFrameSum(Frame current, int pins){
        boolean isNotTenthFrame = current.getIndex() < 9;
        boolean isSecondRoll = current.getRolls().size() == 1;

        if (isNotTenthFrame && isSecondRoll && current.getRolls().get(0) + pins > 10){
            throw new IllegalArgumentException("Dos tiros del frame no pueden sumar mas de 10");
        }
    }

    private void advanceFrame(Frame current){
        frames.add(new Frame(current.getIndex() + 1));
    }
    /** 
     * Registra pinos derribados. Lanza IllegalArgumentException si pines < 0 o > 10. 
     *  Lanza IllegalStateException si el juego ya termino. 
     */ 
    public void roll(int pins) {
        if (isComplete()){
            throw new IllegalStateException("El juego ya termino");
        }
        validatePins(pins);
        Frame current = currentFrame();
        validateFrameSum(current, pins);
        current.addRoll(pins);

        boolean isNotTenthFrame = current.getIndex() < 9;
        if (isNotTenthFrame && current.isComplete()) {
            advanceFrame(current);
        }
    }

    /** Puntaje total. Lanza IllegalStateException si el juego no esta completo. */ 
    public int score() { 
        // TODO: implementar con TDD 
        return 0; 
    } 
    
    /** true cuando los 10 frames han sido completados. */ 
    public boolean isComplete() { 
        if (frames.size() < MAX_FRAMES) return false;
        return frames.get(MAX_FRAMES - 1).isComplete();
    } 
    
    public List<Frame> getFrames() { return List.copyOf(frames); } 

}