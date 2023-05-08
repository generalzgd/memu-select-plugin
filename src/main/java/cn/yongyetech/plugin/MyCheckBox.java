package cn.yongyetech.plugin;

import javax.swing.*;

public class MyCheckBox extends JCheckBox {
    private int val;

//    public MyCheckBox(){
//        super();
//    }

    public MyCheckBox(String text){
        super(text);
    }

    public void setVal(int val){
        this.val = val;
    }

    public int getVal(){
        return this.val;
    }
}
