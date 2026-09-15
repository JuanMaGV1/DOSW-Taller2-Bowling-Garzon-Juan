package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {
    
    private int sumRolls(Frame f){
        return f.getRolls().stream().mapToInt(Integer::intValue).sum();
    }
    public int calculate(List<Frame> frames){
        int total = 0;
        for (Frame f : frames){
            total += sumRolls(f);
        }
        return total;
    }
}
