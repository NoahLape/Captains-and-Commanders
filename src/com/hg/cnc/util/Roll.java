package com.hg.cnc.util;

public class Roll {
    private final int diceCount;

    public Roll(int diceCount) {
        this.diceCount = diceCount;
    }

    public int d(int sides) {
        int total = 0;
        for (int i = 0; i < diceCount; i ++) {
            total += Math.floor(Math.random() * sides) + 1;
        }
        return total;
    }

}