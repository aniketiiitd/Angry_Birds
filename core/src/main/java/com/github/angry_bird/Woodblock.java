package com.github.angry_bird;

public class Woodblock extends Block{
    public Woodblock(float x, float y, String str,int val) {
        super(x, y, str,val);
        //super.rotate(val);
        if(str=="woodframe.png")
            {super.setSize(100,100);}

    }
}
