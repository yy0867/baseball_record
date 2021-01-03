package com.harry.yaguban;

public class Person {
    public Person(String personName,int backNum,Position position)
    {
        this.personName=personName;
        this.backNum=backNum;
        this.position=position;
    }

    public String getName(){return personName;}
    public int getBackNum(){return backNum;}
    public Position getPosition(){return position;}

    private String personName;
    private int backNum;
    private Position position;
}

enum Position{
    PITCHER,CATCHER,FIRST,SECOND,THIRD,SHORTSTOP,LEFT,CENTER,RIGHT,DH;
}
