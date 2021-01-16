package com.harry.yaguban;

public class Batter {
    public Batter() {
        ball4 = 0;
        strikeOut = 0;
        hit = 0; hit2 = 0; hit3 = 0; homerun = 0; totalHit = 0; RBI = 0;
        appearance = 0; atBat = 0; sacrificeFly = 0;
        battingAvg = 0; onBaseAvg = 0; sluggingAvg = 0;
    }

    //increase var
    public void increaseBall4() { ball4++; calculate(); }
    public void increaseStrikeOut() { strikeOut++; calculate(); }
    public void increaseHit() { hit++; totalHit++; calculate(); }
    public void increaseHit2() { hit2++; totalHit++; calculate(); }
    public void increaseHit3() { hit3++; totalHit++; calculate(); }
    public void increaseHomerun() { homerun++; totalHit++; calculate(); }
    public void increaseAppearance() { appearance++; calculate(); }
    public void increaseAtBat() { atBat++; calculate(); }
    public void increaseSacrificeFly() { sacrificeFly++; calculate(); }
    public void increaseRBI() { RBI++; calculate(); }

    //calculate func
    public void calculate() {
        calcBattingAvg();
        calcOnBaseAvg();
        calcSluggingAvg();
    }

    private void calcBattingAvg() {
        if (atBat == 0) battingAvg = 0;
        else battingAvg = totalHit / (double)atBat;
    }

    private void calcOnBaseAvg() {
        onBaseAvg = (double) (totalHit + ball4) / (atBat + ball4 + sacrificeFly);
    }

    private void calcSluggingAvg() {
        sluggingAvg = (hit + 2 * hit2 + 3 * hit3 + 4 * homerun) / (double)atBat;
    }

    private int ball4;
    private int strikeOut, sacrificeFly;
    private int hit, hit2, hit3, homerun, totalHit, RBI;
    private int appearance; //count of batter box
    private int atBat;
    private double battingAvg, onBaseAvg, sluggingAvg;
}
