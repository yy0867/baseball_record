package com.harry.yaguban;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.Serializable;

public class Person implements Serializable {
    public Person(String personName,String position,String backNum)
    {
        this.personName=personName;
        this.backNum=backNum;
        this.position=position;
    }

    public void setName(String name){personName=name;}
    public void setBackNum(String bNum){backNum=bNum;}
    public void setPosition(String pos){position=pos;}

    public String getName(){return personName;}
    public String getBackNum(){return backNum;}
    public String getPosition(){return position;}

    public String convertSaveType(){
        return personName+"\n"+position+"\n"+backNum+"\n";
    }

    public boolean isBatter() {
        return !position.equals("투수");
    }

    //record var
    public Batter batter;
    public Pitcher pitcher;

    private String personName;
    private String position;
    private String backNum;
}
/*
enum Position{
    PITCHER,CATCHER,FIRST,SECOND,THIRD,SHORTSTOP,LEFT,CENTER,RIGHT,DH;
}
*/