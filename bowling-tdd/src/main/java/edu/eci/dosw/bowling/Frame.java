package edu.eci.dosw.bowling;

import java.util.List;
import java.util.ArrayList;

public class Frame {
    private final List<Integer> rolls = new ArrayList<>();
    private final int index;

    public Frame(int index){
        this.index = index;
    }

    public void addRoll(int pins){
        rolls.add(pins);
    }

    public List<Integer> getRolls() {
        return List.copyOf(rolls);
    }

    public int getIndex(){
        return index;
    }

    public boolean isStrike(){
        return !rolls.isEmpty() && rolls.get(0) == 10 && index <9;
    }

    public boolean isSpare() {
        return rolls.size() == 2
                && rolls.get(0) + rolls.get(1) == 10
                && rolls.get(0) != 10;
    }

    public FrameType getType() {
        if (index == 9) return FrameType.TENTH;
        if (isStrike()) return FrameType.STRIKE;
        if (isSpare()) return FrameType.SPARE;
        return FrameType.NORMAL;
    }
}
