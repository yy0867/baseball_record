package com.harry.yaguban;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Game implements Serializable {
    public Game(String homeTeam, String awayTeam, Date date) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        curInning = 0;
        curOut = 0;
        curBattingOrder = 1;
        scoreHome = new int[inning];
        scoreAway = new int[inning];
        batterList = new Person[maxBatter];
        pitcherList = new Vector<>();
        curAttackTeam = away;
    }

    //Getter
    public String getDateString() {
        //Get Date Information to String [2020년 1월 1일 15시]
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        return dateFormat.format(this.date);
    }

    public boolean getOurStatus() {
        if(curAttackTeam == home && homeTeam.equals(ourTeam))
            return attack;
        else if(curAttackTeam == away && awayTeam.equals(ourTeam))
            return attack;

        return defense;
    }

    public int getInning() { return curInning; }
    public int getCurOut() { return curOut; }
    public boolean getCurAttackTeam() { return curAttackTeam; }
    public boolean getCurDefenseTeam() { return !curAttackTeam; }
    public int getTeamScore(boolean whichTeam, int index) { return whichTeam == home ? scoreHome[index] : scoreAway[index]; }
    public String getHomeTeam() { return homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public String getOpponentTeam() { return homeTeam.equals(ourTeam) ? awayTeam : homeTeam; }

    //for Player add / delete / getter
    public int getCurBattingOrder() { return curBattingOrder; }
    public Person getCurBatter() throws NullPointerException {
        return batterList[curBattingOrder];
    }
    public Person getCurPitcher() { return pitcherList.lastElement(); }

    public void addNewBatter(Person p) {
        batterList[curBattingOrder] = p;
    }

    public void addNewPitcher(Person p) {
        pitcherList.add(p);
    }

    //Setter
    public void setScoreHome() { scoreHome[curInning]++; }
    public void setScoreAway() { scoreAway[curInning]++; }
    public void changeCurAttackTeam() { curAttackTeam = !curAttackTeam; }

    //public Func
    public void increaseOut() {
        nextBatter();
        if (curOut == 0 || curOut == 1) {
            plusOut(); // just increase 1 out
        } else if (curOut == 2) {
            plusOut(); // out count become 3 out, change to 0 out
            if (curAttackTeam == away) {
                curAttackTeam = home; // offense, defense switch
            } else if (curAttackTeam == home) {
                curAttackTeam = away; // offense, defense switch
                curInning++;
            }
        }
    }

    public void nextBatter() {
        if (getOurStatus() == attack)
            curBattingOrder = (curBattingOrder + 1) % maxBatter;
    }

    //private Func
    private void plusOut() {
        curOut = (curOut + 1) % maxOut;
    }
    private boolean isThreeOut(int curOut) { return curOut == maxOut; }

    //variables
    static final int inning = 9;
    static final int maxBatter = 9;
    static final int maxPitcher = 9;
    static final int maxOut = 3;
    static final boolean home = true;
    static final boolean away = false;
    static final boolean attack = true;
    static final boolean defense = false;
    static final String ourTeam = "광운대학교";
    private final String homeTeam;
    private final String awayTeam;
    private int[] scoreHome;
    private int[] scoreAway;
    private int curInning;
    private int curOut;
    private int curBattingOrder;
    private boolean curAttackTeam;
    private final Date date;
    private Person[] batterList;
    private Vector<Person> pitcherList;
}
