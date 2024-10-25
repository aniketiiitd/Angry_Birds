package com.github.angry_bird;

public class Blackbird extends Bird{
    public Blackbird(float x, float y, String str) {
        super(x, y, str);
        super.setSize(60,78);
    }

    @Override
    public void specialmove(){}
}
