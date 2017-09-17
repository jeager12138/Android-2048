package com.example.my_2048;

import android.content.Context;
import android.text.TextPaint;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import static android.R.attr.label;
import static android.R.attr.text;

public class Card extends FrameLayout {

    private int num;
    private TextView textView;
    //construct function
    public Card(Context context) {

        super(context);

        textView=new TextView(getContext());
        textView.setTextSize(38);

        TextPaint paint=textView.getPaint();
        paint.setFakeBoldText(true);

        textView.setBackgroundResource(R.drawable.num_2);
        textView.setGravity(Gravity.CENTER);
        //fill the parent textView
        LayoutParams lp=new LayoutParams(-1,-1);

        lp.setMargins(14,14,14,14);
        addView(textView,lp);

        //set num in the textView
        setNum(0);
        showNum();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int n) {
        this.num=n;
        }
    public void showNum() {
        setWordColor();
        if(num==0)
            textView.setText("");
        else
            textView.setText(num+"");

    }
    //judge if they are equals
    public boolean equals(Card x) {
        return getNum()==x.getNum();
    }

    public void setBGColor() {
        switch (num) {
            case 2:
                textView.setBackgroundResource(R.drawable.num_2);
                break;
            case 4:
                textView.setBackgroundResource(R.drawable.num_4);
                break;
            case 8:
                textView.setBackgroundResource(R.drawable.num_8);
                break;
            case 16:
                textView.setBackgroundResource(R.drawable.num_16);
                break;
            case 32:
                textView.setBackgroundResource(R.drawable.num_32);
                break;
            case 64:
                textView.setBackgroundResource(R.drawable.num_64);
                break;
            case 128:
                textView.setBackgroundResource(R.drawable.num_128);
                break;
            case 256:
                textView.setBackgroundResource(R.drawable.num_256);
                break;
            case 512:
                textView.setBackgroundResource(R.drawable.num_512);
                break;
            case 1024:
                textView.setBackgroundResource(R.drawable.num_1024);
                break;
            case 2048:
                textView.setBackgroundResource(R.drawable.num_2048);
                break;
            case 4096:
                textView.setBackgroundResource(R.drawable.num_4096);
                break;
            case 8192:
                textView.setBackgroundResource(R.drawable.num_8192);
                break;
            case 16384:
                textView.setBackgroundResource(R.drawable.num_16384);
                break;
            default:
                textView.setBackgroundResource(R.drawable.num_0);
                break;
        }
    }
    private void setWordColor() {
        switch (num) {
            case 2:
                textView.setTextColor(0xff776E65);
                break;
            case 4:
                textView.setTextColor(0xff776E65);
                break;
            default:
                textView.setTextColor(0xffF9F6F2);
                break;
        }
        if(num>=128&&Config.LINES == 5) {
            textView.setTextSize(32);
        }
        if(num>=1024) {
            textView.setTextSize(34);
            if(Config.LINES == 5)
                textView.setTextSize(24);
        }
        if(num>=16384)
            textView.setTextSize(26);
    }

    public TextView getTextView() {
        return textView;
    }


}
