package com.harry.yaguban;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Person {
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

    private String personName;
    private String position;
    private String backNum;
}
/*
enum Position{
    PITCHER,CATCHER,FIRST,SECOND,THIRD,SHORTSTOP,LEFT,CENTER,RIGHT,DH;
}
*/