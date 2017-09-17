package com.example.my_2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.CornerPathEffect;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.left;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.R.transition.move;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class GameView extends GridLayout {

    private Card[][] cardsArray=new Card[Config.LINES][Config.LINES];
    private List<Point> emptyCards=new ArrayList<Point>();
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    private void initGameView() {
        //4*4 map
        setColumnCount(Config.LINES);
        //setBackgroundColor(0xffbbada0);
        //setBackgroundColor(0xff607D8B);
        //listen to user's touch event
        setOnTouchListener(new View.OnTouchListener() {
        private float down_X,down_Y,move_X,move_Y;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {

                    // get start point
                    case MotionEvent.ACTION_DOWN:
                        down_X = event.getX();
                        down_Y = event.getY();
                        break;
                    //get end point and...
                    case MotionEvent.ACTION_UP:
                        move_X = event.getX() - down_X;
                        move_Y = event.getY() - down_Y;
                        // X is right
                        if (Math.abs(move_X) > Math.abs(move_Y)) {
                            // move to right
                            if (move_X > 10) {
                                moveRight();
                            }
                            //move to left
                            else if (move_X < -10) {
                                moveLeft();
                            }
                        }
                        //Y is right
                        else {
                            //move down
                            if (move_Y > 10) {
                                moveDown();
                            }
                            //move up
                            else if (move_Y < -10) {
                                moveUp();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    //set up once when onCreate
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Config.CARD_WIDTH=(Math.min(w,h))/Config.LINES;

        addCards(Config.CARD_WIDTH,Config.CARD_WIDTH);

        startGame();
    }
    public void finish() {
        this.finish();
    }
    public void addCards(int cardWidth,int cardHeight) {
        Card c;
        for(int y=0;y<Config.LINES;y++) {
            for(int x=0;x<Config.LINES;x++) {
                c=new Card(getContext());
                //Log.d("TAG","1");
                c.setNum(0);
                c.showNum();
                c.setBGColor();

                //System.out.println("2");

                addView(c,cardWidth,cardWidth);
                //Log.d("TAG","3");
                // put the card in the array
                cardsArray[x][y]=c;
            }
        }
    }
    public void startGame() {
        for(int y=0;y<Config.LINES;y++) {
            for(int x=0;x<Config.LINES;x++) {
                cardsArray[x][y].setNum(0);
                cardsArray[x][y].showNum();
                cardsArray[x][y].setBGColor();

            }
        }
        MainActivity.getMainActivity().showHighScore();
        MainActivity.getMainActivity().addScore(0);
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {
        emptyCards.clear();
        for(int y=0;y<Config.LINES;y++) {
            for(int x=0;x<Config.LINES;x++) {
                //if it's a empty card , add in the list
                if(cardsArray[x][y].getNum()==0) {
                    emptyCards.add(new Point(x,y));
                }
            }
        }
        Point p=emptyCards.remove((int)(Math.random()*emptyCards.size()));
        if(Config.SPECIAL_PATTERN==0)
            cardsArray[p.x][p.y].setNum(2);
        else
            cardsArray[p.x][p.y].setNum(2048);
        cardsArray[p.x][p.y].showNum();
        cardsArray[p.x][p.y].setBGColor();


        MainActivity.getMainActivity().getAnimLayout().scaleAnimation(cardsArray[p.x][p.y]);

    }
    private void moveLeft() {
        boolean merge = false;

        for (int y = 0; y < Config.LINES; y++) {
            for(int x = 0; x < Config.LINES; x++){

                for(int x1 = x+1; x1 < Config.LINES; x1++){
                    //now it's not blank
                    if(cardsArray[x1][y].getNum()>0){
                        //exchange the num
                        if(cardsArray[x][y].getNum()==0){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x1][y],cardsArray[x][y], x1, x, y, y);

                            cardsArray[x][y].setNum(cardsArray[x1][y].getNum());

                            cardsArray[x1][y].setNum(0);
                            cardsArray[x1][y].showNum();
                            cardsArray[x1][y].setBGColor();


                            x--;
                            merge = true;
                            //
                        }else if(cardsArray[x][y].equals(cardsArray[x1][y])){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().playHitMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x1][y],cardsArray[x][y], x1, x, y, y);

                            if(Config.SPECIAL_PATTERN==0)
                            cardsArray[x][y].setNum(cardsArray[x][y].getNum()*2);
                            else {
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum() / 2);
                                if(cardsArray[x][y].getNum() == 1) {
                                    cardsArray[x][y].setNum(0);
                                }
                            }
                            cardsArray[x1][y].setNum(0);
                            cardsArray[x1][y].showNum();
                            cardsArray[x1][y].setBGColor();
                            MainActivity.getMainActivity().getAnimLayout().scaleAnimation2(cardsArray[x][y]);
                            MainActivity.getMainActivity().addScore(cardsArray[x][y].getNum());
                            if(Config.SPECIAL_PATTERN == 0) {
                                if (MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE) {
                                    Config.HIGH_SCORE = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            else {
                                if(MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE2) {
                                    Config.HIGH_SCORE2 = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge){
            addRandomNum();
            checkFailure();
        }

    }
    private void moveRight() {

        boolean merge = false;

        for (int y = 0; y < Config.LINES; y++) {
            for(int x = Config.LINES-1; x >= 0; x--){

                for(int x1 = x-1; x1 >= 0; x1--){
                    if(cardsArray[x1][y].getNum()>0){
                        //
                        if(cardsArray[x][y].getNum()==0){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x1][y], cardsArray[x][y],x1, x, y, y);
                            cardsArray[x][y].setNum(cardsArray[x1][y].getNum());
                            cardsArray[x1][y].setNum(0);
                            cardsArray[x1][y].showNum();
                            cardsArray[x1][y].setBGColor();


                            x++;
                            merge = true;
                            //
                        }else if(cardsArray[x][y].equals(cardsArray[x1][y])){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().playHitMusic();

                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x1][y], cardsArray[x][y],x1, x, y, y);

                            if(Config.SPECIAL_PATTERN==0)
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum()*2);
                            else
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum()/2);

                            cardsArray[x1][y].setNum(0);
                            cardsArray[x1][y].showNum();
                            cardsArray[x1][y].setBGColor();
                            MainActivity.getMainActivity().getAnimLayout().scaleAnimation2(cardsArray[x][y]);

                            MainActivity.getMainActivity().addScore(cardsArray[x][y].getNum());
                            if(Config.SPECIAL_PATTERN == 0) {
                                if (MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE) {
                                    Config.HIGH_SCORE = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            else {
                                if(MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE2) {
                                    Config.HIGH_SCORE2 = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge){
            addRandomNum();
            checkFailure();
        }

    }
    private void moveUp() {

        boolean merge = false;

        for (int x = 0; x < Config.LINES; x++) {
            for(int y = 0; y < Config.LINES; y++){

                for(int y1 = y+1; y1 < Config.LINES; y1++){
                    if(cardsArray[x][y1].getNum()>0){
                        //
                        if(cardsArray[x][y].getNum()==0){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x][y1],cardsArray[x][y], x, x, y1, y);
                            cardsArray[x][y].setNum(cardsArray[x][y1].getNum());
                            cardsArray[x][y1].setNum(0);
                            cardsArray[x][y1].showNum();
                            cardsArray[x][y1].setBGColor();


                            y--;
                            merge = true;
                            //
                        }else if(cardsArray[x][y].equals(cardsArray[x][y1])){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().playHitMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x][y1],cardsArray[x][y], x, x, y1, y);

                            if(Config.SPECIAL_PATTERN==0)
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum()*2);
                            else
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum()/2);

                            cardsArray[x][y1].setNum(0);
                            cardsArray[x][y1].showNum();
                            cardsArray[x][y1].setBGColor();
                            MainActivity.getMainActivity().getAnimLayout().scaleAnimation2(cardsArray[x][y]);

                            MainActivity.getMainActivity().addScore(cardsArray[x][y].getNum());
                            if(Config.SPECIAL_PATTERN == 0) {
                                if (MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE) {
                                    Config.HIGH_SCORE = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            else {
                                if(MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE2) {
                                    Config.HIGH_SCORE2 = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge){
            addRandomNum();
            checkFailure();
        }

    }
    private void moveDown() {

        boolean merge = false;

        for (int x = 0; x < Config.LINES; x++) {
            for(int y = Config.LINES-1; y >= 0; y--){

                for(int y1 = y-1; y1 >= 0; y1--){
                    if(cardsArray[x][y1].getNum()>0){
                        //
                        if(cardsArray[x][y].getNum()==0){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x][y1],cardsArray[x][y], x, x, y1, y);

                            cardsArray[x][y].setNum(cardsArray[x][y1].getNum());
                            cardsArray[x][y1].setNum(0);
                            cardsArray[x][y1].showNum();
                            cardsArray[x][y1].setBGColor();


                            y++;
                            merge = true;
                            //
                        }else if(cardsArray[x][y].equals(cardsArray[x][y1])){
//                            MainActivity.getMainActivity().playMoveMusic();
                            MainActivity.getMainActivity().playHitMusic();
                            MainActivity.getMainActivity().getAnimLayout().createMoveAnim(cardsArray[x][y1],cardsArray[x][y], x, x, y1, y);

                            if(Config.SPECIAL_PATTERN==0)
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum()*2);
                            else
                                cardsArray[x][y].setNum(cardsArray[x][y].getNum()/2);

                            cardsArray[x][y1].setNum(0);
                            cardsArray[x][y1].showNum();
                            cardsArray[x][y1].setBGColor();

                            MainActivity.getMainActivity().getAnimLayout().scaleAnimation2(cardsArray[x][y]);

                            MainActivity.getMainActivity().addScore(cardsArray[x][y].getNum());
                            if(Config.SPECIAL_PATTERN == 0) {
                                if (MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE) {
                                    Config.HIGH_SCORE = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            else {
                                if(MainActivity.getMainActivity().getScore() > Config.HIGH_SCORE2) {
                                    Config.HIGH_SCORE2 = MainActivity.getMainActivity().getScore();
                                    MainActivity.getMainActivity().saveHighScore();
                                }
                            }
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge){
            addRandomNum();
            checkFailure();
        }

    }

    private void checkFailure() {

        boolean complete = true;

        ALL:
        for (int y = 0; y < Config.LINES; y++) {
            for(int x = 0; x < Config.LINES; x++){
                if(cardsArray[x][y].getNum()==0||
                        (x>0&&cardsArray[x][y].equals(cardsArray[x-1][y]))||
                        (x<Config.LINES-1 &&cardsArray[x][y].equals(cardsArray[x+1][y]))||
                        (y>0&&cardsArray[x][y].equals(cardsArray[x][y-1]))||
                        (y<Config.LINES-1 &&cardsArray[x][y].equals(cardsArray[x][y+1]))){

                    complete = false;
                    break ALL;
                }
            }
        }

        if(complete){
            MainActivity.getMainActivity().playFailMusic();
            new AlertDialog.Builder(getContext()).setTitle("2048").
                    setMessage("游戏结束，你的得分："+MainActivity.getMainActivity().getScore()).setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    MainActivity.getMainActivity().clearScore();
                    startGame();
                }
            }).show();
        }
    }

}
