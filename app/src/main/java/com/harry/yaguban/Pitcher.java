package com.harry.yaguban;

public class Pitcher {
    public Pitcher() {
        ball4 = 0; strikeOut = 0;
        pitchCount = 0;
        ERA = 0;
        allowHit = 0; losePoint = 0;
        innings = 0;
    }

    public void calculate() {
        calcERA();
    }

    private void calcERA() {
        ERA = (losePoint * 9) / innings;
    }

    public void increaseInnings() {
        innings += 0.1;
        if(innings == 0.3) {
            innings += 0.7;
        }
        calculate();
    }

    public void increaseBall4() { ball4++; calculate(); }
    public void increaseStrikeOut() { strikeOut++; calculate(); }
    public void increasePitchCount() { pitchCount++; calculate(); }
    public void increaseAllowHit() { allowHit++; calculate(); }
    public void increaseLosePoint() { losePoint++; calculate(); }

    private int ball4, strikeOut;
    private int pitchCount;
    private double ERA, innings;
    private int allowHit, losePoint;
}
