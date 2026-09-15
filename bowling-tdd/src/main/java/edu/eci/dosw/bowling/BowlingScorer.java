package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {
    public int calculate(List<Frame> frames){
        int total = 0;
        for (Frame f : frames){
            total += f.getRolls().stream().mapToInt(Integer::intValue).sum();
        }
        return total;
    }
}
