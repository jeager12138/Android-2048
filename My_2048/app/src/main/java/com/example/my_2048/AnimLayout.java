package com.example.my_2048;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.animation;

public class AnimLayout extends FrameLayout {

    public AnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public AnimLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimLayout(Context context) {
        super(context);
    }

    public void createMoveAnim(final Card from,final Card to,int fromX,int toX,int fromY,int toY) {

        final Card c = getCard(from.getNum());
        c.setBGColor();
        LayoutParams lp = new LayoutParams(Config.CARD_WIDTH, Config.CARD_WIDTH);
        lp.leftMargin = fromX*Config.CARD_WIDTH;
        lp.topMargin = fromY*Config.CARD_WIDTH;
        c.setLayoutParams(lp);

        //if (to.getNum()<=0) {
        //    to.getTextView().setVisibility(View.INVISIBLE);
        //}
        TranslateAnimation ta = new TranslateAnimation(0, Config.CARD_WIDTH*(toX-fromX), 0, Config.CARD_WIDTH*(toY-fromY));
        ta.setDuration(150);
        ta.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            //    to.getTextView().setVisibility(View.VISIBLE);
                recycleCard(c);
                to.showNum();
                to.setBGColor();
            }
        });
        c.startAnimation(ta);
    }

    private Card getCard(int num){
        Card c;
        if (cards.size()>0) {
            c = cards.remove(0);
        }else{
            c = new Card(getContext());
            addView(c);
        }
        c.setVisibility(View.VISIBLE);
        c.setNum(num);
        c.showNum();
        return c;
    }
    private void recycleCard(Card c){
        c.setVisibility(View.INVISIBLE);
        c.setAnimation(null);
        cards.add(c);
    }
    private List<Card> cards = new ArrayList<Card>();

    public void scaleAnimation(Card target){
        ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(200);
        target.setAnimation(null);
        target.setBGColor();
        target.showNum();
        target.getTextView().startAnimation(sa);
    }
    public void scaleAnimation2(Card target){
        ScaleAnimation sa2 = new ScaleAnimation(1, 1.3f,1, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa2.setDuration(50);
        target.setAnimation(null);
        target.setBGColor();
        target.showNum();
        target.getTextView().startAnimation(sa2);
        ScaleAnimation sa = new ScaleAnimation(1.3f, 1,1.3f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(50);
        target.setAnimation(null);
        target.getTextView().startAnimation(sa);
    }
}
