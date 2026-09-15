package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {
    
    private int sumRolls(Frame f){
        return f.getRolls().stream().mapToInt(Integer::intValue).sum();
    }

    public int calculate(List<Frame> frames) {
    int total = 0;
    for (int i = 0; i < frames.size(); i++) {
        Frame f = frames.get(i);
        if (f.getIndex() == 9) {
            total += sumRolls(f);
        } else if (f.isStrike()) {
            total += 10 + strikeBonus(frames, i);
        } else if (f.isSpare()) {
            total += 10 + spareBonus(frames, i);
        } else {
            total += sumRolls(f);
        }
    }
    return total;
}

    private int strikeBonus(List<Frame> frames, int i) {
        int bonus = 0;
        int rollsNeeded = 2;
        for (int j = i + 1; j < frames.size() && rollsNeeded > 0; j++) {
            for (int r : frames.get(j).getRolls()) {
                if (rollsNeeded == 0) break;
                bonus += r;
                rollsNeeded--;
            }
        }
        return bonus;
    }

    private int spareBonus(List<Frame> frames, int i) {
        return firstRollOf(frames, i + 1);
    }

    private int firstRollOf(List<Frame> frames, int i) {
        if (i >= frames.size()) return 0;
        Frame f = frames.get(i);
        return f.getRolls().isEmpty() ? 0 : f.getRolls().get(0);
    }
}
