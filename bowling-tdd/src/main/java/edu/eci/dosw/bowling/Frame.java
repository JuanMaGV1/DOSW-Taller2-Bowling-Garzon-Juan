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
}
